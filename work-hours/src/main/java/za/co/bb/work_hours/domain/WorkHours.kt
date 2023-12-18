package za.co.bb.work_hours.domain

import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.Rand
import za.co.bb.core.domain.WorkHoursId

data class WorkHours(
    val employeeId: String,
    val hours: Long,
    val wageId: String,
    val wageRate: Rand,
    val creationDate: LocalDateTime,
    val id: WorkHoursId
)
