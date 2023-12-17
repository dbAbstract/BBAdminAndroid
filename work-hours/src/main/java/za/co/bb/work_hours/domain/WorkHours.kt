package za.co.bb.work_hours.domain

data class WorkHours(
    val employeeId: String,
    val hours: Long,
    val wageId: String
)
