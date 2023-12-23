package za.co.bb.feature_auth.presentation

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
