package ru.ttb220.network.api.mapper

import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.network.api.model.request.TransactionCreateRequestDto
import ru.ttb220.network.api.model.request.TransactionUpdateRequestDto
import ru.ttb220.network.api.model.response.TransactionDetailedResponseDto
import ru.ttb220.network.api.model.response.TransactionResponseDto

fun TransactionDetailedResponseDto.toTransaction(): Transaction =
    Transaction(
        id = id,
        accountId = account.id,
        categoryId = category.id,
        amount = amount.toDouble(),
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun TransactionResponseDto.toTransaction(): Transaction = Transaction(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toDouble(),
    transactionDate = transactionDate,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Transaction.toTransactionCreateRequestDto(): TransactionCreateRequestDto =
    TransactionCreateRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toString(),
        transactionDate = transactionDate,
        comment = comment
    )

fun Transaction.toTransactionUpdateRequestDto(): TransactionUpdateRequestDto =
    TransactionUpdateRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toString(),
        transactionDate = transactionDate,
        comment = comment
    )


fun TransactionBrief.toTransactionCreateRequestDto() = TransactionCreateRequestDto(
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toString(),
    transactionDate = transactionDate,
    comment = comment
)


fun TransactionBrief.toTransactionUpdateRequestDto() = TransactionUpdateRequestDto(
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toString(),
    transactionDate = transactionDate,
    comment = comment
)