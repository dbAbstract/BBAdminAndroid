package za.co.bb.home.di

import za.co.bb.employees.di.EmployeeDependencyContainer
import za.co.bb.home.domain.usecase.GetWageStatusForEmployees
import za.co.bb.work_hours.di.WorkHoursDependencyContainer

internal object DependencyContainer {
    val getWageStatusForEmployees: GetWageStatusForEmployees
        get() = GetWageStatusForEmployees(
            employeeRepository = EmployeeDependencyContainer.employeeRepository,
            workHoursRepository = WorkHoursDependencyContainer.workHoursRepository
        )
}