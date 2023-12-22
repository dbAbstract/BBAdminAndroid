package za.co.bb.wages.data.wages.entity

import za.co.bb.core.util.fromEpochSeconds
import za.co.bb.wages.domain.model.Wage

internal data class WageEntity(
    val desc: String? = null,
    val issueDate: Long? = null,
    val amount: Double? = null
)

internal fun WageEntity.toWage(wageId: String): Wage? {
    return if (desc != null &&
        issueDate != null &&
        amount != null) {
        Wage(
            description = desc,
            issueDate = fromEpochSeconds(issueDate),
            amount = amount,
            id = wageId
        )
    } else null
}
