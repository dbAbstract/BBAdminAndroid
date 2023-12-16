package za.co.bb.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.wages.domain.repository.WageRepository

internal class HomeScreenViewModelFactory(
    private val employeeRepository: EmployeeRepository,
    private val wageRepository: WageRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(
                employeeRepository = employeeRepository,
                wageRepository = wageRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}