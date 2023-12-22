package za.co.bb.work_status.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.domain.Rand
import za.co.bb.core.domain.format
import za.co.bb.core.presentation.BaseViewModel
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.work_hours.domain.WorkHoursRepository
import za.co.bb.work_status.domain.model.WorkStatus
import za.co.bb.work_status.domain.usecase.GetWorkStatuses

internal class WorkStatusHomeViewModel(
    employeeId: EmployeeId,
    private val getWorkStatuses: GetWorkStatuses,
    private val employeeRepository: EmployeeRepository,
    private val workHoursRepository: WorkHoursRepository
) : BaseViewModel<WorkStatusHomeAction>() {
    private val _uiState: MutableStateFlow<WorkStatusHomeScreenState> = MutableStateFlow(
        WorkStatusHomeScreenState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        populateWorkStatuses(employeeId = employeeId)
    }

    private fun calculateTotalWage(workStatuses: List<WorkStatus>): Rand {
        return workStatuses.sumOf {
            it.hours * it.wageRate
        }.format()
    }

    val workStatusHomeEventHandler = object : WorkStatusHomeEventHandler {
        override fun onBack() {
            emitAction(WorkStatusHomeAction.NavigateBack)
        }

        override fun onWorkStatusSelected(workStatus: WorkStatus) {
            if (uiState.value !is WorkStatusHomeScreenState.Loaded) return

            val selectedWorkStatuses = (uiState.value as WorkStatusHomeScreenState.Loaded)
                .selectedWorkStatuses
                .toMutableSet()

            if (!selectedWorkStatuses.contains(workStatus)) {
                selectedWorkStatuses.add(workStatus)

                _uiState.set {
                    it.copy(
                        selectedWorkStatuses = selectedWorkStatuses
                    )
                }
            }
        }

        override fun onWorkStatusDeselected(workStatus: WorkStatus) {
            if (uiState.value !is WorkStatusHomeScreenState.Loaded) return

            val selectedWorkStatuses = (uiState.value as WorkStatusHomeScreenState.Loaded)
                .selectedWorkStatuses
                .toMutableSet()

            if (selectedWorkStatuses.contains(workStatus)) {
                selectedWorkStatuses.remove(workStatus)

                _uiState.set {
                    it.copy(
                        selectedWorkStatuses = selectedWorkStatuses
                    )
                }
            }
        }

        override fun deleteSelectedWorkStatuses() {
            val currentUiState = (uiState.value as? WorkStatusHomeScreenState.Loaded) ?: return

            viewModelScope.launch {
                val deletionResult = workHoursRepository.deleteWorkHourItems(
                    workHoursIdList = currentUiState.selectedWorkStatuses.map { it.workHoursId }
                )
                if (deletionResult.isSuccess) {
                    val updatedWorkStatuses = getWorkStatuses.execute(employeeId)
                    if (updatedWorkStatuses.isSuccess) {
                        _uiState.set {
                            it.copy(
                                workStatuses = updatedWorkStatuses.getOrThrow(),
                                selectedWorkStatuses = emptySet(),
                                totalWage = calculateTotalWage(workStatuses = updatedWorkStatuses.getOrThrow())
                            )
                        }
                    } else {
                        emitAction(WorkStatusHomeAction.ShowError(message = "Unable to refresh work statuses."))
                    }
                } else {
                    emitAction(WorkStatusHomeAction.ShowError(message = "Unable to delete work statuses."))
                }
            }
        }

        override fun navigateToAddWorkStatus() {
            emitAction(WorkStatusHomeAction.NavigateToAddWorkStatus)
        }

        override fun refresh() {
            populateWorkStatuses(employeeId)
        }
    }

    private fun populateWorkStatuses(employeeId: EmployeeId) {
        viewModelScope.launch {
            val employeeResult = async {
                employeeRepository.getEmployee(employeeId = employeeId)
            }.await()
            val workStatusesResult = async {
                getWorkStatuses.execute(employeeId = employeeId)
            }.await()
            if (employeeResult.isSuccess && workStatusesResult.isSuccess) {
                Log.i(TAG, "Successfully got work statuses for empId=$employeeId")
                _uiState.update {
                    WorkStatusHomeScreenState.Loaded(
                        employee = employeeResult.getOrThrow(),
                        workStatuses = workStatusesResult.getOrThrow(),
                        totalWage = calculateTotalWage(workStatuses = workStatusesResult.getOrThrow()),
                        selectedWorkStatuses = emptySet()
                    )
                }
            } else {
                Log.e(TAG, "Error getting work statuses for empId=$employeeId")
            }
        }
    }

    private fun MutableStateFlow<WorkStatusHomeScreenState>.set(function: (WorkStatusHomeScreenState.Loaded) -> WorkStatusHomeScreenState) {
        value.let { currentValue ->
            if (currentValue is WorkStatusHomeScreenState.Loaded) {
                update { function(currentValue) }
            }
        }
    }
}

private const val TAG = "WorkStatus-ViewModel"