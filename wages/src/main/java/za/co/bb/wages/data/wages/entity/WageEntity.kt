package za.co.bb.wages.data.wages.entity

import za.co.bb.wages.domain.model.Rate
import za.co.bb.wages.domain.model.Wage

data class WageEntity(
    val id: String? = null,
    val employeeId: String? = null,
    val issueDate: String? = null,
    val amount: Double? = null,
    val rate: String? = null
)

internal fun WageEntity.toWage(): Wage? {
    if (id != null &&
            employeeId != null &&
            issueDate != null &&
            amount != null &&
            rate != null) {
        val parsedRate = try {
            Rate.valueOf(rate)
        } catch (t: Throwable) {
            null
        }
        parsedRate?.let {
            return Wage(
                id = id,
                employeeId = employeeId,
                issueDate = issueDate,
                amount = amount,
                rate = it
            )
        } ?: return null
    } else return null
}
