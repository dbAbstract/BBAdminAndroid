package za.co.bb.employees.data

import za.co.bb.employees.domain.model.Employee

internal data class EmployeeEntity(
    val firstName: String? = null,
    val surname: String? = null,
    val middleName: String? = null,
    val age: Int? = null
)

internal fun EmployeeEntity.toEmployee(employeeId: String): Employee? {
    return if (firstName != null && surname != null && age != null) {
        Employee(
            id = employeeId,
            firstName = firstName,
            surname = surname,
            middleName = middleName,
            age = age
        )
    } else null
}
