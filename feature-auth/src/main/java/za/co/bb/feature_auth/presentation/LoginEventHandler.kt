package za.co.bb.feature_auth.presentation

import androidx.compose.runtime.Stable

@Stable
interface LoginEventHandler {
    fun onLogin()

    fun onEmailChanged(text: String)

    fun onPasswordChanged(text: String)
}