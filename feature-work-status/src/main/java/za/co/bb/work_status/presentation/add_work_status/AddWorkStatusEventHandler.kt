package za.co.bb.work_status.presentation.add_work_status

import androidx.compose.runtime.Stable

@Stable
interface AddWorkStatusEventHandler {
    fun navigateBack()
    fun onWageSelected(wageIndex: Int)
    fun onWorkingHoursInput(hours: String)
    fun onAddWorkStatusClick()
    fun addWorkStatus()
}