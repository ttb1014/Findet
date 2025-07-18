package ru.ttb220.database.util

import kotlinx.datetime.Instant

interface Timestamped {

    val createdAt: Instant
    val updatedAt: Instant
}