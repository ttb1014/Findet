package ru.ttb220.model.mapper

import ru.ttb220.model.Category
import ru.ttb220.model.account.Account
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.model.transaction.TransactionDetailed

fun Transaction.toTransactionBrief() = TransactionBrief(
    accountId = accountId,
    categoryId = categoryId,
    amount = amount,
    transactionDate = transactionDate,
    comment = comment
)

fun Transaction.toTransactionDetailed(
    account: Account,
    categories: List<Category>
): TransactionDetailed {
    val category = categories.first { it.id == this.categoryId }

    return TransactionDetailed(
        id = id,
        account = account.toAccountState(),
        category = category,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Transaction.toTransactionDetailed(
    account: Account,
    category: Category,
) = TransactionDetailed(
    id = id,
    account = account.toAccountState(),
    category = category,
    amount = amount,
    transactionDate = transactionDate,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt
)