package za.co.bb.home.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import za.co.bb.core.domain.Rand
import za.co.bb.employees.domain.model.Employee
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.home.domain.model.WageStatus
import za.co.bb.work_hours.domain.WorkHours
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class GetWageStatusForEmployees(
    private val employeeRepository: EmployeeRepository,
    private val workHoursRepository: WorkHoursRepository
) {
    suspend fun execute(): List<WageStatus> = coroutineScope {
        var wageStatuses: List<WageStatus> = emptyList()
        ifSuccessfullyRetrievedEmployees { employees ->
            wageStatuses = employees.mapNotNull { employee ->
                val workHoursDueResult = async(Dispatchers.IO) {
                    workHoursRepository.getHoursDueForEmployee(employeeId = employee.id)
                }.await()

                if (workHoursDueResult.isSuccess) {
                    val workHours = workHoursDueResult.getOrThrow()

                    WageStatus(
                        employee = employee,
                        amountDue = calculateTotalWage(workHoursList = workHours),
                        hoursUnpaid = workHours.sumOf { it.hours }
                    )
                } else {
                    null
                }
            }
        }
        wageStatuses
    }


    private fun calculateTotalWage(workHoursList: List<WorkHours>): Rand = workHoursList.sumOf {
        it.wageRate * it.hours
    }

    private suspend fun ifSuccessfullyRetrievedEmployees(
        block: suspend (employees: List<Employee>) -> Unit
    ) {
        val employeesResult = employeeRepository.getEmployees()
        if (employeesResult.isSuccess) {
            withContext(Dispatchers.IO) {
                block(employeesResult.getOrThrow())
            }
        }
    }
}