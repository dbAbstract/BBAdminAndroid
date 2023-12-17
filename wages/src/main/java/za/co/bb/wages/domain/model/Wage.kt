package za.co.bb.wages.domain.model

import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.Rand

data class Wage(
    val id: String,
    val employeeId: String,
    val issueDate: LocalDateTime,
    val amount: Rand
)