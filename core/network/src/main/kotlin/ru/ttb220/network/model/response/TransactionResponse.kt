package ru.ttb220.network.model.response

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: Instant,
    val comment: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
)
