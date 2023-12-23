package za.co.bb.feature_auth.presentation

sealed interface LoginScreenAction {
    data object NavigateToAdminHome : LoginScreenAction
    data object NavigateToEmployeeHome : LoginScreenAction
    data class ShowMessage(val message: String) : LoginScreenAction
}