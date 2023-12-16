package za.co.bb.home.domain.usecase

import za.co.bb.employees.domain.model.Employee
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.home.domain.model.WageStatus
import za.co.bb.wages.domain.repository.WageRepository

internal class GetWageStatusForEmployees(
    private val wageRepository: WageRepository,
    private val employeeRepository: EmployeeRepository
) {
    suspend fun invoke(): List<WageStatus> {
        var wageStatuses: List<WageStatus> = emptyList()

        ifSuccessfullyRetrievedEmployees { employees ->
            wageStatuses = employees.mapNotNull { employee ->
                val wage = wageRepository.getCurrentWageForEmployee(employeeId = employee.id)
                wage.getOrNull()?.let {
                    WageStatus(
                        employee = employee,
                        wage = it,
                        amountDue = 0.0, // TODO - Implement
                        hoursUnpaid = 0 // TODO - Implement
                    )
                }
            }
        }
        return wageStatuses
    }

    private suspend fun ifSuccessfullyRetrievedEmployees(
        block: suspend (employees: List<Employee>) -> Unit
    ) {
        val employeesResult = employeeRepository.getEmployees()
        if (employeesResult.isSuccess) { block(employeesResult.getOrThrow()) }
    }
}
