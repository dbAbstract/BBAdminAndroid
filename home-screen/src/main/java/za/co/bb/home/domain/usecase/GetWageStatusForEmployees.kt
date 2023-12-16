package za.co.bb.home.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
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
    suspend fun execute(): List<WageStatus> = withContext(Dispatchers.IO) {
        coroutineScope {
            var wageStatuses: List<WageStatus> = emptyList()
            ifSuccessfullyRetrievedEmployees { employees ->
                wageStatuses = employees.mapNotNull { employee ->
                    val wageResultDeferred = async {
                        wageRepository.getCurrentWageForEmployee(employeeId = employee.id)
                    }
                    val workHoursDueResultDeferred = async {
                        workHoursRepository.getHoursDue(employeeId = employee.id)
                    }

                    val wageResult = wageResultDeferred.await()
                    val workHoursDueResult = workHoursDueResultDeferred.await()

                    if (wageResult.isSuccess && workHoursDueResult.isSuccess) {
                        val wage = wageResult.getOrThrow()
                        val workHours = workHoursDueResult.getOrThrow()

                        WageStatus(
                            employee = employee,
                            wage = wageResult.getOrThrow(),
                            amountDue = wage.amount * workHours.hours,
                            hoursUnpaid = workHours.hours
                        )
                    } else {
                        null
                    }
                }
            }
            wageStatuses
        }
    }

    private suspend fun ifSuccessfullyRetrievedEmployees(
        block: suspend (employees: List<Employee>) -> Unit
    ) {
        val employeesResult = employeeRepository.getEmployees()
        if (employeesResult.isSuccess) { block(employeesResult.getOrThrow()) }
    }
}

private const val TAG = "GetWageStatusForEmployees"