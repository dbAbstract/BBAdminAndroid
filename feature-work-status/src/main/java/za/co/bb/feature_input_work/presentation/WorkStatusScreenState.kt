package za.co.bb.feature_input_work.presentation

import za.co.bb.employees.domain.model.Employee

internal data class WorkStatusScreenState(
    val employees: List<Employee> = emptyList()
)
