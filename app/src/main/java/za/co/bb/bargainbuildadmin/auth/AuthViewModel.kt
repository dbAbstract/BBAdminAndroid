package za.co.bb.bargainbuildadmin.auth

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

class AuthViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<AuthActions>() {

    private val _state = MutableStateFlow(AuthState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val isLoggedIn = userRepository.isLoggedIn
            val currentUserResult = userRepository.getCurrentUser()

            _state.update {
                it.copy(
                    isLoading = false,
                    isUserLoggedIn = isLoggedIn,
                    userType = currentUserResult.getOrNull()?.userType
                )
            }
        }
    }
}

private class AuthViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(
                userRepository = UserDependencyContainer.userRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun authViewModel(): AuthViewModel =
    viewModel(factory = AuthViewModelFactory())