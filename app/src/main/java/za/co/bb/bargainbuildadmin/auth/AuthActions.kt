package za.co.bb.bargainbuildadmin.auth

sealed interface AuthActions {
    data object NavigateToAdmin : AuthActions

    data object NavigateToEmployee : AuthActions
}