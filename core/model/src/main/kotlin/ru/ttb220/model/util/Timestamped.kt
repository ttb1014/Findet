package ru.ttb220.model.util

import kotlinx.datetime.Instant

interface Timestamped {

    val createdAt: Instant
    val updatedAt: Instant
}