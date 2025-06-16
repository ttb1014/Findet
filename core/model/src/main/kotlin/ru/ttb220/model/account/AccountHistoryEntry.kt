package ru.ttb220.model.account

import kotlinx.datetime.Instant

data class AccountHistoryEntry(
    val id: Int,
    val accountId: Int,
    val changeType: AccountChangeType,
    val previousState: AccountState?,
    val newState: AccountState,
    val changeTimestamp: Instant,
    val createdAt: Instant,
)
