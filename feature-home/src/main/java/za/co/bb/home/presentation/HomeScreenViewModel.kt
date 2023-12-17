package za.co.bb.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import za.co.bb.home.domain.usecase.GetWageStatusForEmployees

internal class HomeScreenViewModel(
    private val getWageStatusForEmployees: GetWageStatusForEmployees
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeScreenState(
            isLoading = true
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        Timber.tag(TAG).i("Initialized!")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    employeeWageStatuses = getWageStatusForEmployees.execute(),
                    isLoading = false
                )
            }
        }
    }
}

private const val TAG = "EmployeeScreenViewModel"