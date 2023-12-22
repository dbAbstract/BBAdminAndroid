package za.co.bb.feature_auth.presentation

internal sealed interface LoginScreenAction {
    data object NavigateToHome : LoginScreenAction
    data class ShowMessage(val message: String) : LoginScreenAction
}