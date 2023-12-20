package za.co.bb.work_status.presentation.add_work_status

import za.co.bb.employees.domain.model.Employee
import za.co.bb.wages.domain.model.Wage
import za.co.bb.work_status.domain.model.WorkStatus

internal data class AddWorkStatusScreenState(
    val workStatus: WorkStatus,
    val employee: Employee? = null,
    val wages: List<Wage>
)
