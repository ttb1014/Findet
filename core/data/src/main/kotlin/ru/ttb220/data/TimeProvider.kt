package ru.ttb220.data

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

interface TimeProvider {

    /**
     * Method should be called just in time
     */
    fun now(): Instant

    fun startOfToday(): Instant

    /**
     * Method should be called just in time
     */
    fun today(): LocalDate
}