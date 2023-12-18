package za.co.bb.home.presentation

import za.co.bb.core.domain.EmployeeId

internal sealed interface HomeScreenAction {
    data object NavigateToAddEmployee : HomeScreenAction
    data class NavigateToWorkStatus(val employeeId: EmployeeId) : HomeScreenAction
}