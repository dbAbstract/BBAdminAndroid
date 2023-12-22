package za.co.bb.work_status.presentation.add_work_status

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.presentation.BaseViewModel
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.wages.domain.repository.WageRepository
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class AddWorkStatusViewModel(
    employeeId: EmployeeId,
    private val workHoursRepository: WorkHoursRepository,
    private val wagesRepository: WageRepository,
    private val employeeRepository: EmployeeRepository
) : BaseViewModel<AddWorkStatusAction>() {

    private val _uiState = MutableStateFlow(
        AddWorkStatusScreenState(
            workStatus = null,
            wages = emptyList()
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val employeeResult = async {
                employeeRepository.getEmployee(employeeId)
            }.await()

            val wagesResult = async {
                wagesRepository.getWages()
            }.await()

            if (employeeResult.isSuccess && wagesResult.isSuccess) {
                val wages = wagesResult.getOrThrow()
                _uiState.update {
                    it.copy(
                        employee = employeeResult.getOrThrow(),
                        wages = wages
                    )
                }

                if (wages.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            workStatus = WorkStatusUiModel(
                                wageId = wages.first().id,
                                wageRate = wages.first().amount,
                                hours = "0"
                            )
                        )
                    }
                }
            }
        }
    }

    val addWorkStatusEventHandler = object : AddWorkStatusEventHandler {
        override fun navigateBack() {
            emitAction(AddWorkStatusAction.NavigateBack)
        }

        override fun onWageSelected(wageIndex: Int) {
            _uiState.update { currentState ->
                currentState.copy(
                    workStatus = currentState.workStatus?.copy(
                        wageId = currentState.wages[wageIndex].id,
                        wageRate = currentState.wages[wageIndex].amount
                    )
                )
            }
        }

        override fun onWorkingHoursInput(hours: String) {
            _uiState.update { currentState ->
                currentState.copy(
                    workStatus = currentState.workStatus?.copy(
                        hours = hours
                    )
                )
            }
        }

        override fun onAddWorkStatusClick() {
            val workStatus = uiState.value.workStatus
            val isInputHoursValid = workStatus != null
                    && workStatus.hours.isDigitsOnly()
                    && workStatus.hours.toLong() > 0
            if (!isInputHoursValid)
                emitAction(AddWorkStatusAction.ShowMessage(message = "Invalid work hours entry."))
            else
                emitAction(AddWorkStatusAction.ShowConfirmation)
        }

        override fun addWorkStatus() {
            val workStatus = uiState.value.workStatus ?: return
            val workHours = try {
                workStatus.hours.toLong()
            } catch (t: Throwable) {
                emitAction(AddWorkStatusAction.ShowMessage(message = "Invalid work hours entry."))
                return
            }

            viewModelScope.launch {
                val addWorkHoursResult = workHoursRepository.addWorkHourForEmployee(
                    employeeId = employeeId,
                    hoursWorked = workHours,
                    wageId = workStatus.wageId,
                    wageRate = workStatus.wageRate
                )

                if (addWorkHoursResult.isSuccess) {
                    emitAction(AddWorkStatusAction.ShowMessage("Successfully added work hours."))
                } else {
                    emitAction(AddWorkStatusAction.ShowMessage("Can't add work hours at this time."))
                }
            }
        }
    }


}