package ru.ttb220.network.api.mapper

import kotlinx.datetime.Instant
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.network.api.model.AccountDto
import ru.ttb220.network.api.model.request.AccountCreateRequestDto
import ru.ttb220.network.api.model.request.AccountUpdateRequestDto
import ru.ttb220.network.api.model.response.AccountDetailedResponseDto
import ru.ttb220.network.api.model.response.AccountHistoryResponseDto
import ru.ttb220.network.api.model.response.AccountResponseDto

fun Account.toAccountRequestDto(): AccountCreateRequestDto = AccountCreateRequestDto(
    name = name,
    balance = balance.toString(),
    currency = currency
)

fun Account.toAccountUpdateRequestDto(): AccountUpdateRequestDto = AccountUpdateRequestDto(
    name = name,
    balance = balance.toString(),
    currency = currency
)

fun AccountDetailedResponseDto.toAccount(userId: Int): Account = Account(
    id = id,
    userId = userId,
    name = name,
    balance = balance.toDouble(),
    currency = currency,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)

fun AccountHistoryResponseDto.toAccount(
    userId: Int,
    createdAt: Instant,
    updatedAt: Instant,
): Account = Account(
    id = accountId,
    userId = userId,
    name = accountName,
    balance = currentBalance.toDouble(),
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun AccountResponseDto.toAccount(): Account = Account(
    id = id,
    userId = userId,
    name = name,
    balance = balance.toDouble(),
    currency = currency,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
)

fun AccountDto.toAccount(
    userId: Int,
    createdAt: Instant,
    updatedAt: Instant,
): Account = Account(
    id = id,
    userId = userId,
    name = name,
    balance = balance.toDouble(),
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Account.toAccountCreateRequestDto(): AccountCreateRequestDto = AccountCreateRequestDto(
    name = name,
    balance = balance.toString(),
    currency = currency
)

fun AccountBrief.toAccountCreateRequestDto(): AccountCreateRequestDto = AccountCreateRequestDto(
    name = name,
    balance = balance.toString(),
    currency = currency
)