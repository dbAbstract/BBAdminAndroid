package za.co.bb.wages.domain.model

import kotlinx.datetime.LocalDateTime

data class Wage(
    val id: String,
    val employeeId: String,
    val issueDate: LocalDateTime,
    val amount: Rand
)

/**
 * South African Rand - ZAR
 */
typealias Rand = Double