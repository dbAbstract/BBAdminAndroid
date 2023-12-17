package za.co.bb.core.domain

import kotlin.math.round

/**
 * South African Rand - ZAR
 */
typealias Rand = Double

fun Double.format(): Rand {
    val factor = 100.0
    return round(this * factor) / factor
}