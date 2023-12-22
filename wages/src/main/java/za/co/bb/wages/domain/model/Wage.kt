package za.co.bb.wages.domain.model

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.Rand
import za.co.bb.core.domain.WageId

@Stable
data class Wage(
    val id: WageId,
    val description: String,
    val issueDate: LocalDateTime,
    val amount: Rand
)