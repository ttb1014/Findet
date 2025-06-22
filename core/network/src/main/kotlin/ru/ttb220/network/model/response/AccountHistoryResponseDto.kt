package ru.ttb220.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.model.account.AccountHistory
import ru.ttb220.network.model.AccountHistoryEntryDto
import ru.ttb220.network.model.toAccountHistoryEntry

@Serializable
data class AccountHistoryResponseDto(
    @SerialName("accountId")
    val accountId: Int,

    @SerialName("accountName")
    val accountName: String,

    @SerialName("currency")
    val currency: String,

    @SerialName("currentBalance")
    val currentBalance: String,

    @SerialName("history")
    val history: List<AccountHistoryEntryDto>,
)

fun AccountHistoryResponseDto.toAccountHistory(): AccountHistory =
    AccountHistory(accountId = accountId,
        accountName = accountName,
        currency = currency,
        currentBalance = currentBalance,
        history = history.map { it.toAccountHistoryEntry() })