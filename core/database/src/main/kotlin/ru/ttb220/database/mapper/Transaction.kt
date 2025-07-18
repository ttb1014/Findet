package ru.ttb220.database.mapper

import kotlinx.datetime.Instant
import ru.ttb220.database.entity.TransactionEntity
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief

fun TransactionEntity.toTransaction(): Transaction = Transaction(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount,
    transactionDate = date,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Transaction.toTransactionEntity(
    synced: Boolean = false
): TransactionEntity = TransactionEntity(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount,
    date = transactionDate,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt,
    synced = synced
)

fun TransactionBrief.toTransactionEntity(
    id: Int = 0,
    synced: Boolean = false,
    createdAt: Instant,
    updatedAt: Instant,
) = TransactionEntity(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount,
    date = transactionDate,
    comment = comment,
    synced = synced,
    createdAt = createdAt,
    updatedAt = updatedAt
)