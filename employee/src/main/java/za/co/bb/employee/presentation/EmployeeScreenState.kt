package za.co.bb.employee.presentation

import za.co.bb.employee.domain.model.Employee

internal data class EmployeeScreenState(
    val employees: List<Employee> = emptyList()
)