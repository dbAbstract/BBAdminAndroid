package za.co.bb.work_hours.domain

interface WorkHoursRepository {
    suspend fun getHoursDueForEmployee(employeeId: String): Result<List<WorkHours>>
}