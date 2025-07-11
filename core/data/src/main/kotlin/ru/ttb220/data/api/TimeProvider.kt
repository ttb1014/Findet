package ru.ttb220.data.api

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

/**
 * Provides time. Emits value only once on each collect block
 */
interface TimeProvider {

    fun now(): Flow<Instant>

    fun startOfToday(): Flow<Instant>

    fun today(): Flow<LocalDate>

    fun startOfAMonth(): Flow<LocalDate>
}