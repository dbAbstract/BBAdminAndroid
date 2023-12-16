package za.co.bb.home.domain

import za.co.bb.employees.domain.model.Employee
import za.co.bb.wages.domain.model.Rand
import za.co.bb.wages.domain.model.Wage

internal data class WageStatus(
    val employee: Employee,
    val wage: Wage,
    val amountDue: Rand,
    val hoursWorked: Int
    // TODO - Add last updated field
)
