package za.co.bb.employee.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import za.co.bb.employee.domain.EmployeeRepository

internal class EmployeeScreenViewModel(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        EmployeeScreenState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val employeesResult = employeeRepository.getEmployees()
            _uiState.update {
                it.copy(employees = employeesResult.getOrDefault(emptyList()))
            }
        }
    }
}

private const val TAG = "EmployeeScreenViewModel"

internal class EmployeeScreenViewModelFactory(
    private val employeeRepository: EmployeeRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeScreenViewModel::class.java)) {
            return EmployeeScreenViewModel(employeeRepository = employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}