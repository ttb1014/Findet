package ru.ttb220.data.api

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

/**
 * Provides time.
 */
interface TimeProvider {

    fun now(): Instant

    fun startOfToday(): Instant

    fun today(): LocalDate

    fun startOfAMonth(): LocalDate
    fun endOfAMonth(): LocalDate
    fun dayMonthAgo(): LocalDate
}