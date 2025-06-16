package ru.ttb220.model.transaction

import kotlinx.datetime.Instant
import ru.ttb220.model.Category
import ru.ttb220.model.account.AccountState

data class TransactionDetailed(
    val id: Int,
    val account: AccountState,
    val category: Category,
    val amount: String,
    val transactionDate: Instant,
    val comment: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
)
