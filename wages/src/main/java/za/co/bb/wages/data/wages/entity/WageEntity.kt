package za.co.bb.wages.data.wages.entity

import za.co.bb.core.util.fromEpochSeconds
import za.co.bb.wages.domain.model.Wage

internal data class WageEntity(
    val id: String? = null,
    val desc: String? = null,
    val employeeId: String? = null,
    val issueDate: Long? = null,
    val amount: Double? = null
)

internal fun WageEntity.toWage(): Wage? {
    return if (desc != null &&
        employeeId != null &&
        issueDate != null &&
        amount != null &&
        id != null) {
        Wage(
            id = id,
            description = desc,
            employeeId = employeeId,
            issueDate = fromEpochSeconds(issueDate),
            amount = amount
        )
    } else null
}
