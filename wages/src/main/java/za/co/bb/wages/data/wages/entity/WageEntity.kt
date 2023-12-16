package za.co.bb.wages.data.wages.entity

import za.co.bb.core.util.fromEpochSeconds
import za.co.bb.wages.domain.model.Wage

data class WageEntity(
    val id: String? = null,
    val employeeId: String? = null,
    val issueDate: Long? = null,
    val amount: Double? = null
)

internal fun WageEntity.toWage(): Wage? {
    return if (id != null &&
        employeeId != null &&
        issueDate != null &&
        amount != null) {
        Wage(
            id = id,
            employeeId = employeeId,
            issueDate = fromEpochSeconds(issueDate),
            amount = amount
        )
    } else null
}
