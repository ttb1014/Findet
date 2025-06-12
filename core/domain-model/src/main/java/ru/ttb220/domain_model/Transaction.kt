package ru.ttb220.domain_model

import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)