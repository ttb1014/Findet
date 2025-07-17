package ru.ttb220.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.model.transaction.TransactionStat

@Entity(
    tableName = "accounts",
    indices = [
        Index(value = ["name"])
    ],
)
data class AccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "balance")
    val balance: Double,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "created_at")
    override val createdAt: Instant,

    @ColumnInfo(name = "updated_at")
    override val updatedAt: Instant,
) : Timestamped

fun AccountEntity.toAccount() = Account(
    id = id,
    userId = userId,
    name = name,
    balance = balance.toString(),
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun AccountEntity.toAccountBrief() = AccountBrief(
    name = name,
    balance = balance.toString(),
    currency = currency
)

fun AccountEntity.toAccountDetailed(
    expenses: List<TransactionStat>,
    incomes: List<TransactionStat>
) = AccountDetailed(
    id = id,
    name = name,
    balance = balance.toString(),
    currency = currency,
    incomes = incomes,
    expenses = expenses,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Account.toAccountEntity() = AccountEntity(
    id = id,
    name = name,
    userId = userId,
    balance = balance.toDouble(),
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)