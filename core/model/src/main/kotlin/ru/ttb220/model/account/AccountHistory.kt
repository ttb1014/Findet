package ru.ttb220.model.account

data class AccountHistory(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history:List<AccountHistoryEntry>
)
