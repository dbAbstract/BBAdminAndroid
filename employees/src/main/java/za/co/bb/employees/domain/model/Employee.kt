package za.co.bb.employees.domain.model

import za.co.bb.core.domain.EmployeeId

data class Employee(
    val id: EmployeeId,
    val firstName: String,
    val surname: String,
    val middleName: String? = null,
    val age: Int
)