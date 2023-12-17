package za.co.bb.feature_input_work.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.presentation.BaseViewModel
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class WorkStatusViewModel(
    employeeId: EmployeeId,
    private val workHoursRepository: WorkHoursRepository,
    private val employeeRepository: EmployeeRepository
) : BaseViewModel<WorkStatusAction>() {
    private val _uiState: MutableStateFlow<WorkStatusScreenState> = MutableStateFlow(WorkStatusScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val employeeResult = employeeRepository.getEmployee(employeeId = employeeId)
            if (employeeResult.isSuccess) {
                _uiState.update {
                    WorkStatusScreenState.Loaded(
                        employee = employeeResult.getOrThrow()
                    )
                }
            }
        }
    }

    val workStatusEventHandler = object : WorkStatusEventHandler {
        override fun onBack() {
            emitAction(WorkStatusAction.NavigateBack)
        }
    }
}