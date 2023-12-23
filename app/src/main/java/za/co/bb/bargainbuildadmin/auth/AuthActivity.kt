package za.co.bb.bargainbuildadmin.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import za.co.bb.bargainbuildadmin.admin.AdminAppActivity
import za.co.bb.bargainbuildadmin.employee.EmployeeAppActivity
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.core.util.collectAction
import za.co.bb.feature_auth.presentation.LoginScreenAction
import za.co.bb.feature_auth.presentation.loginViewModel
import za.co.bb.feature_auth.ui.LoginScreen
import za.co.bb.user.domain.model.UserType

class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AuthActivityContent()
        }
    }

    @Composable
    private fun AuthActivityContent() {
        val authViewModel = authViewModel()
        val authState by authViewModel.state.collectAsStateWithLifecycle()
        val userType = remember(authState) {
            authState.userType
        }

        when {
            authState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppColors.current.primary
                    )
                }
            }

            authState.isUserLoggedIn && userType != null -> {
                navigateAccordingToUserType(userType)
            }

            else -> {
                val loginViewModel = loginViewModel()
                val loginState by loginViewModel.uiState.collectAsStateWithLifecycle()

                LoginScreen(
                    uiState = loginState,
                    loginEventHandler = loginViewModel.loginEventHandler
                )

                loginViewModel.collectAction { action ->
                    when (action) {
                        LoginScreenAction.NavigateToEmployeeHome ->
                            navigateAccordingToUserType(userType = UserType.Employee)

                        LoginScreenAction.NavigateToAdminHome ->
                            navigateAccordingToUserType(userType = UserType.Admin)

                        is LoginScreenAction.ShowMessage -> showMessage(action.message)
                    }
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateAccordingToUserType(userType: UserType) {
        when (userType) {
            UserType.Employee -> {
                val intent = Intent(this, EmployeeAppActivity::class.java)
                startActivity(intent)
                finish()
            }

            UserType.Admin -> {
                val intent = Intent(this, AdminAppActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}