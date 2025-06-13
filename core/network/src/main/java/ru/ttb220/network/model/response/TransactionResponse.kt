package ru.ttb220.network.model.response

import kotlinx.datetime.LocalDateTime

data class TransactionResponse(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
