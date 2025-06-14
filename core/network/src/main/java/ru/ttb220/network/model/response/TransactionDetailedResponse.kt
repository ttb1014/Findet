package ru.ttb220.network.model.response

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ru.ttb220.network.model.Account
import ru.ttb220.network.model.Category

@Serializable
data class TransactionDetailedResponse(
    val id: Int,
    val account: Account,
    val category: Category,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)