package za.co.bb.home.domain.usecase

import kotlinx.coroutines.coroutineScope
import za.co.bb.core.domain.Rand
import za.co.bb.core.domain.format
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.home.domain.model.WageStatus
import za.co.bb.work_hours.domain.WorkHours
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class GetWageStatusForEmployees(
    private val employeeRepository: EmployeeRepository,
    private val workHoursRepository: WorkHoursRepository
) {
    suspend fun execute(): List<WageStatus> = try {
        coroutineScope {
            val employees = employeeRepository.getEmployees()
                .getOrThrow()
                .toSet()
            val employeeIds = employees.map { it.id }
            workHoursRepository.getHoursDueForEmployees(employeeIds)
                .getOrThrow()
                .mapNotNull { map ->
                    employees.find { it.id == map.key }?.let { employee ->
                        WageStatus(
                            employee = employee,
                            amountDue = calculateTotalWage(workHoursList = map.value),
                            hoursUnpaid = map.value.sumOf { it.hours }
                        )
                    }
                }
        }
    } catch (t: Throwable) {
        emptyList()
    }


    private fun calculateTotalWage(workHoursList: List<WorkHours>): Rand = workHoursList.sumOf {
        it.wageRate * it.hours
    }.format()
}