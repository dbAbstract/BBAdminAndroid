package za.co.bb.feature_input_work.domain.model

import za.co.bb.core.domain.EmployeeId

internal data class WorkStatus(
    val employee: Employee
)

internal data class Employee(
    val id: EmployeeId,
    val firstName: String,
    val surname: String,
    val middleName: String? = null,
    val age: Int
)