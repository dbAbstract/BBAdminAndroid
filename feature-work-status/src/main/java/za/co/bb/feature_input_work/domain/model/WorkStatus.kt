package za.co.bb.feature_input_work.domain.model

import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.Rand

internal data class WorkStatus(
    val hours: Int,
    val wageRate: Rand,
    val wageId: String,
    val createdAt: LocalDateTime,
    val amountDue: Rand
)