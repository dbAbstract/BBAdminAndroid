package za.co.bb.feature_auth.presentation

internal data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
