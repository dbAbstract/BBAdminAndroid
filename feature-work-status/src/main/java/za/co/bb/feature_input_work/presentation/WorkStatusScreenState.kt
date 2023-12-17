package za.co.bb.feature_input_work.presentation

import za.co.bb.employees.domain.model.Employee
import za.co.bb.feature_input_work.domain.model.WorkStatus

internal sealed interface WorkStatusScreenState {
    data object Error : WorkStatusScreenState

    data object Loading : WorkStatusScreenState

    data class Loaded(
        val employee: Employee,
        val workStatuses: List<WorkStatus>
    ) : WorkStatusScreenState
}
