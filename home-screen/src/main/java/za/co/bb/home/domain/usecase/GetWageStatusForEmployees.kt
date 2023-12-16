package za.co.bb.home.domain.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import za.co.bb.employees.domain.model.Employee
import za.co.bb.employees.domain.repository.EmployeeRepository
import za.co.bb.home.domain.model.WageStatus
import za.co.bb.wages.domain.repository.WageRepository
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class GetWageStatusForEmployees(
    private val wageRepository: WageRepository,
    private val employeeRepository: EmployeeRepository,
    private val workHoursRepository: WorkHoursRepository
) {
    suspend fun execute(): List<WageStatus> = coroutineScope {
        var wageStatuses: List<WageStatus> = emptyList()
        Timber.tag(TAG).i("Now trying get Wage Statuses")
        ifSuccessfullyRetrievedEmployees { employees ->
            Timber.tag(TAG).i("Got ${employees.size} employees")
            wageStatuses = employees.mapNotNull { employee ->
                val wageResult = async {
                    wageRepository.getCurrentWageForEmployee(employeeId = employee.id)
                }.await()
                val workHoursDueResult = async {
                    workHoursRepository.getHoursDue(employeeId = employee.id)
                }.await()

                if (wageResult.isSuccess && workHoursDueResult.isSuccess) {
                    val wage = wageResult.getOrThrow()
                    val workHours = workHoursDueResult.getOrThrow()
                    Timber.tag(TAG).i("Successfully acquired wage and work hours")
                    WageStatus(
                        employee = employee,
                        wage = wageResult.getOrThrow(),
                        amountDue = wage.amount * workHours,
                        hoursUnpaid = workHours
                    )
                } else {
                    Timber.tag(TAG).i("Failed to acquire wage and work hours")
                    null
                }
            }
        }
        wageStatuses
    }

    private suspend fun ifSuccessfullyRetrievedEmployees(
        block: suspend (employees: List<Employee>) -> Unit
    ) {
        val employeesResult = employeeRepository.getEmployees()
        if (employeesResult.isSuccess) { block(employeesResult.getOrThrow()) }
    }
}

private const val TAG = "GetWageStatusForEmployees"