package za.co.bb.home.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.presentation.BaseViewModel
import za.co.bb.home.domain.usecase.GetWageStatusForEmployees

internal class HomeScreenViewModel(
    private val getWageStatusForEmployees: GetWageStatusForEmployees
) : BaseViewModel<HomeScreenAction>() {
    private val _uiState = MutableStateFlow(
        HomeScreenState(
            isLoading = true
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        Log.i(TAG, "Initialized!")
        viewModelScope.launch {
            val employeesWageStatuses = getWageStatusForEmployees.execute()
            if (employeesWageStatuses.isNotEmpty()) _uiState.update {
                it.copy(
                    employeeWageStatuses = employeesWageStatuses,
                    isLoading = false
                )
            } else {
                emitAction(HomeScreenAction.ShowError(message = "Could not get Work status for employees at this time."))
            }
        }
    }

    val homeScreenEventHandler = object : HomeScreenEventHandler {
        override fun onAddEmployeeClick() {
            emitAction(HomeScreenAction.NavigateToAddEmployee)
        }

        override fun navigateToWorkStatus(employeeId: EmployeeId) {
            emitAction(HomeScreenAction.NavigateToWorkStatus(employeeId = employeeId))
        }
    }
}

private const val TAG = "EmployeeScreenViewModel"