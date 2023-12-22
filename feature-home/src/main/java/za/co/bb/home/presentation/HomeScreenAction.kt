package za.co.bb.home.presentation

import za.co.bb.core.domain.EmployeeId

internal sealed interface HomeScreenAction {

    data class NavigateToWorkStatus(val employeeId: EmployeeId) : HomeScreenAction

    data class ShowError(val message: String) : HomeScreenAction
}