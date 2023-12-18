package za.co.bb.feature_input_work.domain.usecase

import android.util.Log
import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.domain.format
import za.co.bb.feature_input_work.domain.model.WorkStatus
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class GetWorkStatuses(
    private val workHoursRepository: WorkHoursRepository
) {
    suspend fun execute(employeeId: EmployeeId): Result<List<WorkStatus>> = try {
        val workHours = workHoursRepository.getHoursDueForEmployee(employeeId = employeeId).getOrThrow()
        Log.i(TAG, "Received workHours=$workHours for empId=$employeeId")
        val workStatuses = workHours.map {
            WorkStatus(
                hours = it.hours,
                wageRate = it.wageRate,
                wageId = it.wageId,
                createdAt = it.creationDate,
                amountDue = (it.wageRate * it.hours).format(),
                workHoursId = it.id
            )
        }
        Log.i(TAG, "Converted WorkHours into $workStatuses")
        Result.success(workStatuses)
    } catch (t: Throwable) {
        Log.i(TAG, "Error converting WorkHours into WorkStatuses: $t")
        Result.failure(t)
    }
}

private const val TAG = "Work-Status-GetWorkStatusesUseCase"