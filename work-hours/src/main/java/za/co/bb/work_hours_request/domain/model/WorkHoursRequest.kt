package za.co.bb.work_hours_request.domain.model

import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.WorkHoursRequestId
import za.co.bb.work_hours.domain.WorkHours

data class WorkHoursRequest(
    val id: WorkHoursRequestId,
    val workHours: WorkHours,
    val requestDate: LocalDateTime,
    val isApproved: Boolean
)
