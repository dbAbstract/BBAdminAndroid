package za.co.bb.bargainbuildadmin.presentation

import za.co.bb.user.domain.model.UserType

data class AppState(
    val isLoading: Boolean = true,
    val isUserLoggedIn: Boolean = false,
    val userType: UserType? = null
)
