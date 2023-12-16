package za.co.bb.wages.data.entity

data class WageEntity(
    val id: String? = null,
    val employeeId: String? = null,
    val issueDate: String? = null,
    val amount: Double? = null,
    val rate: String? = null
)
