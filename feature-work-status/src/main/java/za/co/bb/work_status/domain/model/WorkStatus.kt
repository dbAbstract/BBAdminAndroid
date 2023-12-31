package za.co.bb.work_status.domain.model

import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.Rand
import za.co.bb.core.domain.WorkHoursId

internal data class WorkStatus(
    val hours: Long,
    val wageRate: Rand,
    val wageId: String,
    val createdAt: LocalDateTime,
    val amountDue: Rand,
    val workHoursId: WorkHoursId
)