package za.co.bb.employee.data

import za.co.bb.employee.domain.model.Employee

internal data class EmployeeEntity(
    val id: String? = null,
    val firstName: String? = null,
    val surname: String? = null,
    val middleName: String? = null,
    val age: Int? = null
)

internal fun EmployeeEntity.toEmployee(): Employee? {
    return if (id != null && firstName != null && surname != null && age != null) {
        Employee(
            id = id,
            firstName = firstName,
            surname = surname,
            middleName = middleName,
            age = age
        )
    } else null
}
