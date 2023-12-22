package za.co.bb.work_status.presentation.home

import androidx.compose.runtime.Stable
import za.co.bb.work_status.domain.model.WorkStatus

@Stable
internal interface WorkStatusHomeEventHandler {
    fun onBack()
    fun onWorkStatusSelected(workStatus: WorkStatus)
    fun onWorkStatusDeselected(workStatus: WorkStatus)
    fun deleteSelectedWorkStatuses()
    fun navigateToAddWorkStatus()
    fun refresh()
}