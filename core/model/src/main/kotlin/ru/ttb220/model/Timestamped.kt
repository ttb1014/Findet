package ru.ttb220.model

import kotlinx.datetime.Instant

interface Timestamped {
    val createdAt: Instant
    val updatedAt: Instant
}