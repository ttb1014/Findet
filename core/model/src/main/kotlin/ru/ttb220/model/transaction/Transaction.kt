package ru.ttb220.model.transaction

import kotlinx.datetime.Instant

data class Transaction(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: Instant,
    val comment: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
)