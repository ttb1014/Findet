package ru.ttb220.data.impl

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.data.api.TimeProvider
import javax.inject.Inject

class DefaultTimeProvider @Inject constructor(
    private val timeZone: TimeZone,
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun endOfAMonth(): LocalDate {
        val today = today()
        val year = today.year
        val month = today.month
        val isLeap = isLeapYear(year)
        val lastDay = month.length(isLeap)
        return LocalDate(year, month, lastDay)
    }

    override fun dayMonthAgo(): LocalDate {
        return today().minus(1, DateTimeUnit.MONTH)
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}