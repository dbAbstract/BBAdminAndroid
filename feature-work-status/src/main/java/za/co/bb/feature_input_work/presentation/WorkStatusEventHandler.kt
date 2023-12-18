package za.co.bb.feature_input_work.presentation

import androidx.compose.runtime.Stable
import za.co.bb.feature_input_work.domain.model.WorkStatus

@Stable
internal interface WorkStatusEventHandler {
    fun onBack()
    fun onWorkStatusSelected(workStatus: WorkStatus)
    fun onWorkStatusDeselected(workStatus: WorkStatus)
    fun deleteSelectedWorkStatuses()
}