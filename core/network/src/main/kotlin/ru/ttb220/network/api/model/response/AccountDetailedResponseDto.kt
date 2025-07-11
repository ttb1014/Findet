package ru.ttb220.network.api.model.response

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.network.api.model.StatItemDto
import ru.ttb220.network.api.model.toTransactionStat

@Serializable
data class AccountDetailedResponseDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("balance")
    val balance: String,

    @SerialName("currency")
    val currency: String,

    @SerialName("incomeStats")
    val incomeStats: List<StatItemDto>,

    @SerialName("expenseStats")
    val expenseStats: List<StatItemDto>,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("updatedAt")
    val updatedAt: String,
)

fun AccountDetailedResponseDto.toAccountDetailed(): AccountDetailed = AccountDetailed(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
    incomes = incomeStats.map { it.toTransactionStat() },
    expenses = expenseStats.map { it.toTransactionStat() },
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
)