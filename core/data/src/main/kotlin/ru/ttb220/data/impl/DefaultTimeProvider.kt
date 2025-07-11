package ru.ttb220.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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

    override fun now(): Flow<Instant> = flow {
        emit(Clock.System.now())
    }

    override fun startOfToday(): Flow<Instant> = flow {
        val today = today().first()
        emit(today.atStartOfDayIn(timeZone))
    }

    override fun today(): Flow<LocalDate> = flow {
        val today = now().first().toLocalDateTime(timeZone).date
        emit(today)
    }

    override fun startOfAMonth(): Flow<LocalDate> = flow {
        val today = today().first()
        emit(LocalDate(today.year, today.month, 1))
    }
}