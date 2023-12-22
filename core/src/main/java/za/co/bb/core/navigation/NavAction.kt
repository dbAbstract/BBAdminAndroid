package za.co.bb.core.navigation

import za.co.bb.core.domain.EmployeeId

sealed interface NavAction {
    data object NavigateToHome : NavAction
    data object NavigateBack : NavAction

    data class NavigateToWorkStatus(
        val employeeId: EmployeeId
    ) : NavAction, ScreenNavigation(Screen.WorkStatusGraph)

    data class NavigateToAddWorkStatus(
        val employeeId: EmployeeId
    ) : NavAction, ScreenNavigation(Screen.AddWorkStatus)
}

abstract class ScreenNavigation(val screen: Screen)