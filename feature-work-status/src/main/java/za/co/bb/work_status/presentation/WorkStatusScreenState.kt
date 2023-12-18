package za.co.bb.work_status.presentation

import za.co.bb.core.domain.Rand
import za.co.bb.employees.domain.model.Employee
import za.co.bb.work_status.domain.model.WorkStatus

internal sealed interface WorkStatusScreenState {
    data object Error : WorkStatusScreenState

    data object Loading : WorkStatusScreenState

    data class Loaded(
        val employee: Employee,
        val workStatuses: List<WorkStatus>,
        val totalWage: Rand,
        val selectedWorkStatuses: Set<WorkStatus>
    ) : WorkStatusScreenState
}
