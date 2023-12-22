package za.co.bb.feature_auth.navigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.util.collectAction
import za.co.bb.feature_auth.presentation.LoginScreenAction
import za.co.bb.feature_auth.presentation.getLoginViewModel
import za.co.bb.feature_auth.ui.LoginScreen

fun NavGraphBuilder.loginNavGraph(navigate: (NavAction) -> Unit) {
    composable(route = Screen.Login.name) {
        val context = LocalContext.current
        val loginViewModel = getLoginViewModel()
        val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

        LoginScreen(
            uiState = uiState,
            loginEventHandler = loginViewModel.loginEventHandler
        )

        loginViewModel.collectAction { action ->
            when (action) {
                LoginScreenAction.NavigateToHome -> navigate(NavAction.NavigateToHome)

                is LoginScreenAction.ShowMessage -> Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}