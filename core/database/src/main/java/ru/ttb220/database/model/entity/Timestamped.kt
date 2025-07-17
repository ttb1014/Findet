package ru.ttb220.database.model.entity

import kotlinx.datetime.Instant


interface Timestamped {
    val createdAt: Instant
    val updatedAt: Instant
}