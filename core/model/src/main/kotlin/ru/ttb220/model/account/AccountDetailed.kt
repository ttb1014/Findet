package ru.ttb220.model.account

import kotlinx.datetime.Instant
import ru.ttb220.model.transaction.TransactionStat

data class AccountDetailed(
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String,
    val incomes: List<TransactionStat>,
    val expenses: List<TransactionStat>,
    val createdAt: Instant,
    val updatedAt: Instant,
)
