package ru.ttb220.domain_model

import kotlinx.datetime.LocalDateTime

data class Account(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
