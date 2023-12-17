package za.co.bb.home.presentation

import za.co.bb.home.domain.model.WageStatus

internal data class HomeScreenState(
    val employeeWageStatuses: List<WageStatus> = emptyList()
)