package za.co.bb.bargainbuildadmin.auth

import za.co.bb.user.domain.model.UserType

data class AuthState(
    val isLoading: Boolean = true,
    val isUserLoggedIn: Boolean = false,
    val userType: UserType? = null
)
