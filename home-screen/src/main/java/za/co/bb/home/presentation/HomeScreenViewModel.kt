package za.co.bb.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.wages.domain.repository.WageRepository

internal class HomeScreenViewModel(
    private val employeeRepository: EmployeeRepository,
    private val wageRepository: WageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeScreenState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {

        }
    }
}

private const val TAG = "EmployeeScreenViewModel"