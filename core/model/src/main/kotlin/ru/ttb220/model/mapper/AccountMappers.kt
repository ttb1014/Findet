package ru.ttb220.model.mapper

import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.model.account.AccountState

fun Account.toAccountBrief(): AccountBrief = AccountBrief(
    name = name,
    balance = balance,
    currency = currency
)

fun AccountDetailed.toAccountBrief() = AccountBrief(
    name = name,
    balance = balance,
    currency = currency
)

fun Account.toAccountState(): AccountState = AccountState(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun AccountDetailed.toAccount(userId: Int) = Account(
    id = id,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)
