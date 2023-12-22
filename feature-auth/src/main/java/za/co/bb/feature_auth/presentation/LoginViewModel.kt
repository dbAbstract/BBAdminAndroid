package za.co.bb.feature_auth.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import za.co.bb.core.presentation.BaseViewModel
import za.co.bb.user.domain.UserRepository
import za.co.bb.user.domain.di.UserDependencyContainer

internal class LoginViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<LoginScreenAction>() {

    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState.asStateFlow()


    val loginEventHandler = object : LoginEventHandler {
        override fun onLogin() {
            viewModelScope.launch {
                val loginResult = userRepository.loginWithEmailAndPassword(
                    email = uiState.value.email,
                    password = uiState.value.password
                )

                if (loginResult.isSuccess) {
                    emitAction(LoginScreenAction.NavigateToHome)
                } else {
                    emitAction(LoginScreenAction.ShowMessage(message = "Incorrect login details."))
                }
            }
        }

        override fun onEmailChanged(text: String) {
            _uiState.update {
                it.copy(email = text)
            }
        }

        override fun onPasswordChanged(text: String) {
            _uiState.update {
                it.copy(password = text)
            }
        }

    }
}

@Suppress("UNCHECKED_CAST")
private class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository = UserDependencyContainer.userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
internal fun getLoginViewModel(): LoginViewModel = viewModel(factory = LoginViewModelFactory())

