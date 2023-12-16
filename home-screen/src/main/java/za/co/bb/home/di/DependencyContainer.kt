package za.co.bb.home.di

import za.co.bb.employees.di.EmployeeDependencyContainer
import za.co.bb.home.domain.usecase.GetWageStatusForEmployees
import za.co.bb.wages.di.WagesDependencyContainer
import za.co.bb.work_hours.di.WorkHoursDependencyContainer

internal object DependencyContainer {
    val getWageStatusForEmployees: GetWageStatusForEmployees
        get() = GetWageStatusForEmployees(
            wageRepository = WagesDependencyContainer.wageRepository,
            employeeRepository = EmployeeDependencyContainer.employeeRepository,
            workHoursRepository = WorkHoursDependencyContainer.workHoursRepository
        )
}