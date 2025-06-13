package ru.ttb220.network.model.response

import ru.ttb220.network.model.AccountHistoryEntry

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryEntry>,
)
