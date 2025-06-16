package ru.ttb220.domain_model.transaction

import kotlinx.datetime.Instant
import ru.ttb220.domain_model.Category
import ru.ttb220.domain_model.account.AccountState

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
