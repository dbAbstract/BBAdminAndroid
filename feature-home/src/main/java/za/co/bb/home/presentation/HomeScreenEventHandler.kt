package za.co.bb.home.presentation

import androidx.compose.runtime.Stable
import za.co.bb.core.domain.EmployeeId

@Stable
interface HomeScreenEventHandler {
    fun navigateToWorkStatus(employeeId: EmployeeId)
    fun refresh()
}