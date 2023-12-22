package za.co.bb.core.domain

import kotlin.math.round

/**
 * South African Rand - ZAR
 */
typealias Rand = Double

fun Rand.print(): String = String.format("%.2f", format())

fun Double.format(): Rand {
    val factor = 100.0
    return round(this * factor) / factor
}

typealias EmployeeId = String
typealias WageId = String
typealias WorkHoursId = String
typealias UserId = String