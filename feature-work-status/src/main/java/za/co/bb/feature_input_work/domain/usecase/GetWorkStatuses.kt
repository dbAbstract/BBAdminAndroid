package za.co.bb.feature_input_work.domain.usecase

import za.co.bb.core.domain.EmployeeId
import za.co.bb.feature_input_work.domain.model.WorkStatus
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class GetWorkStatuses(
    private val workHoursRepository: WorkHoursRepository
) {
    suspend fun execute(employeeId: EmployeeId): Result<List<WorkStatus>> = try {
        val workHours = workHoursRepository.getHoursDueForEmployee(employeeId = employeeId).getOrThrow()
        val workStatuses = workHours.map {
            WorkStatus(
                hours = it.hours,
                wageRate = it.wageRate,
                wageId = it.wageId,
                createdAt = it.creationDate,
                amountDue = it.wageRate * it.hours
            )
        }
        Result.success(workStatuses)
    } catch (t: Throwable) {
        Result.failure(t)
    }
}