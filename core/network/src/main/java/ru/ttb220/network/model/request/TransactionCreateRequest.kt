package ru.ttb220.network.model.request

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TransactionCreateRequest(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: Instant,
    val comment: String?
)