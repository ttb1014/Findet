package ru.ttb220.domain_model.account

import kotlinx.datetime.Instant

data class Account(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
