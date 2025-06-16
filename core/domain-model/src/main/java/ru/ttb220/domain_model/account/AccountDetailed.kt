package ru.ttb220.domain_model.account

import kotlinx.datetime.Instant
import ru.ttb220.domain_model.transaction.TransactionStat

data class AccountDetailed(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomes: List<TransactionStat>,
    val expenses: List<TransactionStat>,
    val createdAt: Instant,
    val updatedAt: Instant,
)
