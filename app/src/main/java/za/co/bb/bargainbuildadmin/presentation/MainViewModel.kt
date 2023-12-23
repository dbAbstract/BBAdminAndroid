package za.co.bb.bargainbuildadmin.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import za.co.bb.user.domain.UserRepository
import za.co.bb.user.domain.di.UserDependencyContainer

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val isLoggedIn = userRepository.isLoggedIn
            _state.update {
                it.copy(
                    isLoading = false,
                    isUserLoggedIn = isLoggedIn
                )
            }
        }
    }
}

private class MainViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                userRepository = UserDependencyContainer.userRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun getMainViewModel(): MainViewModel =
    viewModel(factory = MainViewModelFactory())