package za.co.bb.home.presentation

internal sealed interface HomeScreenAction {
    data object NavigateToAddEmployee : HomeScreenAction
}