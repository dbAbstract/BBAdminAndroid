package za.co.bb.wages.domain.model

data class Wage(
    val id: String,
    val employeeId: String,
    val issueDate: String,
    val amount: Rand,
    val rate: Rate = Rate.Hourly
)

typealias Rand = Double