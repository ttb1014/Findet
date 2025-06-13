package ru.ttb220.network.model

import kotlinx.datetime.LocalDateTime

data class AccountHistoryEntry(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeType,
    val previousState: AccountState?,
    val newState: AccountState,
    val changeTimeStamp: LocalDateTime,
    val createdAt: LocalDateTime,
)
