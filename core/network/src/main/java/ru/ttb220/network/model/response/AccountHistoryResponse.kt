package ru.ttb220.network.model.response

import kotlinx.serialization.Serializable
import ru.ttb220.network.model.AccountHistoryEntry

@Serializable
data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryEntry>,
)
