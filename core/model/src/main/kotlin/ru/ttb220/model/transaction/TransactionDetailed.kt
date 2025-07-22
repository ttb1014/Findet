package ru.ttb220.model.transaction

import kotlinx.datetime.Instant
import ru.ttb220.model.Category
import ru.ttb220.model.account.AccountState
import ru.ttb220.model.util.Timestamped
import ru.ttb220.model.util.Updatable

data class TransactionDetailed(
    val id: Int,
    val account: AccountState,
    val category: Category,
    val amount: Double,
    val transactionDate: Instant,
    val comment: String? = null,
    override val createdAt: Instant,
    override val updatedAt: Instant,
) : Timestamped, Updatable<TransactionDetailed> {

    override fun update(instant: Instant): TransactionDetailed =
        this.copy(updatedAt = instant)
}