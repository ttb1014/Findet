package ru.ttb220.model.transaction

import kotlinx.datetime.Instant
import ru.ttb220.model.Category
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountState
import ru.ttb220.model.account.toAccountState

data class TransactionDetailed(
    val id: Int,
    val account: AccountState,
    val category: Category,
    val amount: Double,
    val transactionDate: Instant,
    val comment: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
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