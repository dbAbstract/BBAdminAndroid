package za.co.bb.work_hours.data

import za.co.bb.work_hours.domain.WorkHours

internal data class WorkHoursEntity(
    val employeeId: String? = null,
    val hoursDue: Long? = null,
    val wageId: String? = null
)

internal fun WorkHoursEntity.toWorkHours(): WorkHours? {
    return if (employeeId != null && hoursDue != null && wageId != null) {
        WorkHours(employeeId, hoursDue, wageId)
    } else null
}