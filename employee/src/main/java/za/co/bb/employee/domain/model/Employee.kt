package za.co.bb.employee.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    @SerialName("id")
    val id: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("middleName")
    val middleName: String? = null,
    @SerialName("age")
    val age: Int
)
