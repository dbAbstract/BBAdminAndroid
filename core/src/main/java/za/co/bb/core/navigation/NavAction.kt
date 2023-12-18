package za.co.bb.core.navigation

import za.co.bb.core.domain.EmployeeId

sealed interface NavAction {
    data object NavigateBack : NavAction

    data object NavigateToAddEmployee : NavAction, ScreenNavigation(Screen.AddEmployee)

    data class NavigateToWorkStatus(
        val employeeId: EmployeeId
    ) : NavAction, ScreenNavigation(Screen.WorkStatus)
}

abstract class ScreenNavigation(val screen: Screen)