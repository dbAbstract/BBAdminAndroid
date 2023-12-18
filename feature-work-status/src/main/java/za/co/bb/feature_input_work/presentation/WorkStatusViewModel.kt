package za.co.bb.feature_input_work.presentation

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
import za.co.bb.feature_input_work.domain.model.WorkStatus
import za.co.bb.feature_input_work.domain.usecase.GetWorkStatuses
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class WorkStatusViewModel(
    employeeId: EmployeeId,
    private val getWorkStatuses: GetWorkStatuses,
    private val employeeRepository: EmployeeRepository,
    private val workHoursRepository: WorkHoursRepository
) : BaseViewModel<WorkStatusAction>() {
    private val _uiState: MutableStateFlow<WorkStatusScreenState> = MutableStateFlow(WorkStatusScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        populateWorkStatuses(employeeId = employeeId)
    }

    private fun calculateTotalWage(workStatuses: List<WorkStatus>): Rand {
        return workStatuses.sumOf {
            it.hours * it.wageRate
        }.format()
    }

    val workStatusEventHandler = object : WorkStatusEventHandler {
        override fun onBack() {
            emitAction(WorkStatusAction.NavigateBack)
        }

        override fun onWorkStatusSelected(workStatus: WorkStatus) {
            if (uiState.value !is WorkStatusScreenState.Loaded) return

            val selectedWorkStatuses = (uiState.value as WorkStatusScreenState.Loaded)
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
            if (uiState.value !is WorkStatusScreenState.Loaded) return

            val selectedWorkStatuses = (uiState.value as WorkStatusScreenState.Loaded)
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
            val currentUiState = (uiState.value as? WorkStatusScreenState.Loaded) ?: return

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
                        emitAction(WorkStatusAction.ShowError(message = "Unable to refresh work statuses."))
                    }
                } else {
                    emitAction(WorkStatusAction.ShowError(message = "Unable to delete work statuses."))
                }
            }
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
                    WorkStatusScreenState.Loaded(
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

    private fun MutableStateFlow<WorkStatusScreenState>.set(function: (WorkStatusScreenState.Loaded) -> WorkStatusScreenState) {
        value.let { currentValue ->
            if (currentValue is WorkStatusScreenState.Loaded) {
                Log.d("lol", "state updated with ${function(currentValue)}")
                update { function(currentValue) }
            }
        }
    }
}

private const val TAG = "WorkStatus-ViewModel"