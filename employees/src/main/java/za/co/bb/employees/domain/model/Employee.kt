package za.co.bb.employees.domain.model

data class Employee(
    val id: String,
    val firstName: String,
    val surname: String,
    val middleName: String? = null,
    val age: Int
)
