package za.co.bb.work_status.presentation.add_work_status

import za.co.bb.core.domain.Rand

data class WorkStatusUiModel(
    val hours: String,
    val wageRate: Rand,
    val wageId: String,
)