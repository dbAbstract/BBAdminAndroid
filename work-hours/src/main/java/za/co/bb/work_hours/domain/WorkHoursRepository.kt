package za.co.bb.work_hours.domain

interface WorkHoursRepository {
    suspend fun getHoursDue(employeeId: String): Result<WorkHours>
}