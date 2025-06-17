package ru.ttb220.data

import kotlinx.datetime.Instant

interface TimeProvider {

    fun now(): Instant

    fun startOfToday(): Instant
}