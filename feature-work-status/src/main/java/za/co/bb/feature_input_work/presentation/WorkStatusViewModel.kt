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

internal class WorkStatusViewModel(
    employeeId: EmployeeId,
    private val getWorkStatuses: GetWorkStatuses,
    private val employeeRepository: EmployeeRepository
) : BaseViewModel<WorkStatusAction>() {
    private val _uiState: MutableStateFlow<WorkStatusScreenState> = MutableStateFlow(WorkStatusScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
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
                        totalWage = calculateTotalWage(workStatuses = workStatusesResult.getOrThrow())
                    )
                }
            } else {
                Log.e(TAG, "Error getting work statuses for empId=$employeeId")
            }
        }
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
    }
}

private const val TAG = "WorkStatus-ViewModel"