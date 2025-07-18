package ru.ttb220.database.mapper

import kotlinx.datetime.Instant
import ru.ttb220.database.entity.AccountEntity
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief

fun AccountEntity.toAccount(): Account = Account(
    id = id,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Account.toAccountEntity(synced: Boolean = false): AccountEntity = AccountEntity(
    synced = synced,
    id = id,
    name = name,
    userId = userId,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun AccountBrief.toAccountEntity(
    id: Int = 0,
    userId: Int,
    timeStamp: Instant,
    synced: Boolean = false
) = AccountEntity(
    id = id,
    name = name,
    userId = userId,
    balance = balance.toDouble(),
    currency = currency,
    synced = synced,
    createdAt = timeStamp,
    updatedAt = timeStamp
)