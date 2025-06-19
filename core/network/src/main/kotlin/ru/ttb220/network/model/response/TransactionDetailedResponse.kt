package ru.ttb220.network.model.response

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.ttb220.network.model.AccountDto
import ru.ttb220.network.model.CategoryDto

@Serializable
data class TransactionDetailedResponse(
    val id: Int,
    val account: AccountDto,
    val category: CategoryDto,
    val amount: String,
    val transactionDate: Instant,
    val comment: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
)