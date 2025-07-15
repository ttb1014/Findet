package ru.ttb220.database.model.entity

import java.time.Instant

interface Timestamped {
    val createdAt: Instant
    val updatedAt: Instant
}