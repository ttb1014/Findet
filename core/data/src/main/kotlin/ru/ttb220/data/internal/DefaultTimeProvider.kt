package ru.ttb220.data.internal

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.data.TimeProvider
import javax.inject.Inject

internal class DefaultTimeProvider @Inject constructor(
    private val timeZone: TimeZone
) : TimeProvider {

    override fun now(): Instant = Clock.System.now()

    override fun startOfToday(): Instant {
        val today = today()
        return today.atStartOfDayIn(timeZone)
    }

    // FIXME: Возвращает пред-предыдущий день
    override fun today(): LocalDate {
        val today = now().toLocalDateTime(timeZone).date.plus(DatePeriod(days = 2))
        return today
    }

    override fun startOfAMonth(): LocalDate {
        val today = today()
        return LocalDate(today.year, today.month, 1)
    }
}