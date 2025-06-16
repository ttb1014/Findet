package ru.ttb220.domain_model.transaction

data class TransactionStat(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String,
)
