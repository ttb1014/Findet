package ru.ttb220.data.impl

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.data.api.TimeProvider
import javax.inject.Inject

class DefaultTimeProvider @Inject constructor(
    private val timeZone: TimeZone
) : TimeProvider {

    override fun now(): Instant {
        return Clock.System.now()
    }

    override fun startOfToday(): Instant {
        val today = today()
        return today.atStartOfDayIn(timeZone)
    }

    override fun today(): LocalDate {
        val today = now().toLocalDateTime(timeZone).date
        return today
    }

    override fun startOfAMonth(): LocalDate {
        val today = today()
        return LocalDate(today.year, today.month, 1)
    }
}