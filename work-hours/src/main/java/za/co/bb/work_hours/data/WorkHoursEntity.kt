package za.co.bb.work_hours.data

import za.co.bb.core.util.fromEpochSeconds
import za.co.bb.work_hours.domain.WorkHours

internal data class WorkHoursEntity(
    val employeeId: String? = null,
    val hoursDue: Long? = null,
    val wageId: String? = null,
    val wageRate: Double? = null,
    val creationDate: Long? = null
)

internal fun WorkHoursEntity.toWorkHours(workHoursId: String): WorkHours? {
    return if (employeeId != null
        && hoursDue != null
        && wageId != null
        && wageRate != null
        && creationDate != null
        ) {
        WorkHours(
            employeeId = employeeId,
            hours = hoursDue,
            wageId = wageId,
            wageRate = wageRate,
            creationDate = fromEpochSeconds(epochSeconds = creationDate),
            id = workHoursId
        )
    } else null
}