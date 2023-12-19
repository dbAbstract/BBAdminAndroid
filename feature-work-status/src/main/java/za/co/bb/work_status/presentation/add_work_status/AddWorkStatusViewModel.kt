package za.co.bb.work_status.presentation.add_work_status

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.presentation.BaseViewModel
import za.co.bb.core.util.now
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.work_hours.domain.WorkHoursRepository
import za.co.bb.work_status.domain.model.WorkStatus

internal class AddWorkStatusViewModel(
    employeeId: EmployeeId,
    private val workHoursRepository: WorkHoursRepository,
    private val employeeRepository: EmployeeRepository
) : BaseViewModel<AddWorkStatusAction>() {

    private val _uiState = MutableStateFlow(
        AddWorkStatusScreenState(
            workStatus = WorkStatus(
                hours = 0,
                wageRate = 0.0,
                wageId = "",
                createdAt = now,
                amountDue = 0.0,
                workHoursId = ""
            ),
            employees = emptyList(),
            wages = emptyList()
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val employees = async {
                employeeRepository.getEmployees()
            }
        }
    }

    val addWorkStatusEventHandler = object : AddWorkStatusEventHandler {
        override fun navigateBack() {
            emitAction(AddWorkStatusAction.ShowConfirmation)
        }
    }


}