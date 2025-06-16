package ru.ttb220.model.transaction

import kotlinx.datetime.Instant

data class TransactionBrief(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: Instant,
    val comment: String? = null,
)