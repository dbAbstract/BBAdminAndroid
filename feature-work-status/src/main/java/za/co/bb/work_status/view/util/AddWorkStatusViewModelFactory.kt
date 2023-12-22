package za.co.bb.work_status.view.util

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import za.co.bb.core.domain.EmployeeId
import za.co.bb.employees.di.EmployeeDependencyContainer
import za.co.bb.wages.di.WagesDependencyContainer
import za.co.bb.work_hours.di.WorkHoursDependencyContainer
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusViewModel

private class AddWorkStatusViewModelFactory(
    private val employeeId: EmployeeId
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddWorkStatusViewModel::class.java)) {
            return AddWorkStatusViewModel(
                employeeId = employeeId,
                workHoursRepository = WorkHoursDependencyContainer.workHoursRepository,
                employeeRepository = EmployeeDependencyContainer.employeeRepository,
                wagesRepository = WagesDependencyContainer.wageRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
internal fun getAddWorkStatusViewModel(employeeId: EmployeeId): AddWorkStatusViewModel {
    return viewModel(
        factory = AddWorkStatusViewModelFactory(employeeId = employeeId)
    )
}