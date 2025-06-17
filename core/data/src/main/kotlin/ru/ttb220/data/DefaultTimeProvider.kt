package ru.ttb220.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

internal class DefaultTimeProvider @Inject constructor(
    private val timeZone: TimeZone
) : TimeProvider {

    override fun now(): Instant = Clock.System.now()

    override fun startOfToday(): Instant {
        val today = now().toLocalDateTime(timeZone).date
        return today.atStartOfDayIn(timeZone)
    }
}