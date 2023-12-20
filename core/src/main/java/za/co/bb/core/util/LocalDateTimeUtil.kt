package za.co.bb.core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

val now: LocalDateTime
    get() = Clock.System.now().toLocalDateTime(timeZone = TimeZone.currentSystemDefault())

fun fromEpochSeconds(epochSeconds: Long): LocalDateTime =
    Instant.fromEpochSeconds(epochSeconds).toLocalDateTime(TimeZone.currentSystemDefault())

fun toEpochSeconds(localDateTime: LocalDateTime): Long =
    localDateTime.toInstant(TimeZone.currentSystemDefault()).epochSeconds

fun LocalDateTime.print(): String =
    "$dayOfMonth-$month-$year $hour:$minute"

