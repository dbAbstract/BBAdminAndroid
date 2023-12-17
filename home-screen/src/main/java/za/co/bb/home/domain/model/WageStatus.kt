package za.co.bb.home.domain.model

import za.co.bb.core.domain.typealiases.Rand
import za.co.bb.employees.domain.model.Employee

internal data class WageStatus(
    val employee: Employee,
    val amountDue: Rand,
    val hoursUnpaid: Long
    // TODO - Add last updated field
)
