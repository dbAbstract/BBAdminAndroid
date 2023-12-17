package za.co.bb.wages.domain.model

import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.Rand
import za.co.bb.core.domain.WageId

data class Wage(
    val id: WageId,
    val description: String,
    val employeeId: String,
    val issueDate: LocalDateTime,
    val amount: Rand
)