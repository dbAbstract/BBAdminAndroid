package za.co.bb.home.domain.model

import za.co.bb.employees.domain.model.Employee
import za.co.bb.wages.domain.model.Rand
import za.co.bb.wages.domain.model.Wage

internal data class WageStatus(
    val employee: Employee,
    val wage: Wage,
    val amountDue: Rand,
    val hoursUnpaid: Int
    // TODO - Add last updated field
)
