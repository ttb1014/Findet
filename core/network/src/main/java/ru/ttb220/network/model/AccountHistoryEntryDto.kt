package ru.ttb220.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class AccountHistoryEntryDto(
    val id: Int,
    val accountId: Int,
    val changeTypeDto: ChangeTypeDto,
    val previousState: AccountStateDto?,
    val newState: AccountStateDto,
    val changeTimeStamp: Instant,
    val createdAt: Instant,
)
