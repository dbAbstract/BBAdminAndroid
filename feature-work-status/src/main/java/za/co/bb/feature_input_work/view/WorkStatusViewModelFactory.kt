package za.co.bb.feature_input_work.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import za.co.bb.core.domain.EmployeeId
import za.co.bb.employees.di.EmployeeDependencyContainer
import za.co.bb.feature_input_work.presentation.WorkStatusViewModel
import za.co.bb.work_hours.di.WorkHoursDependencyContainer

private class InputWorkViewModelFactory(
    private val employeeId: EmployeeId
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkStatusViewModel::class.java)) {
            return WorkStatusViewModel(
                employeeId = employeeId,
                employeeRepository = EmployeeDependencyContainer.employeeRepository,
                getWorkStatuses = DependencyContainer.getWorkStatuses,
                workHoursRepository = WorkHoursDependencyContainer.workHoursRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
internal fun getWorkStatusViewModel(employeeId: EmployeeId): WorkStatusViewModel {
    return viewModel(
        factory = InputWorkViewModelFactory(employeeId)
    )
}