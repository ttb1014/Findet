package ru.ttb220.network.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.network.api.model.AccountHistoryEntryDto

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