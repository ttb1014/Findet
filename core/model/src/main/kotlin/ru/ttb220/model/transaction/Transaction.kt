package ru.ttb220.model.transaction

import kotlinx.datetime.Instant
import ru.ttb220.model.Timestamped
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountState

data class Transaction(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: Double,
    val transactionDate: Instant,
    val comment: String? = null,
    override val createdAt: Instant,
    override val updatedAt: Instant,
) : Timestamped