package ru.ttb220.network.api.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.model.account.AccountHistoryEntry

@Serializable
data class AccountHistoryEntryDto(
    @SerialName("id")
    val id: Int,

    @SerialName("accountId")
    val accountId: Int,

    @SerialName("changeType")
    val changeType: ChangeTypeDto,

    @SerialName("previousState")
    val previousState: AccountStateDto?,

    @SerialName("newState")
    val newState: AccountStateDto,

    @SerialName("changeTimeStamp")
    val changeTimeStamp: Instant,

    @SerialName("createdAt")
    val createdAt: Instant,
)

fun AccountHistoryEntryDto.toAccountHistoryEntry() = AccountHistoryEntry(
    id = id,
    accountId = accountId,
    changeType = changeType.toAccountChangeType(),
    previousState = previousState?.toAccountState(),
    newState = newState.toAccountState(),
    changeTimestamp = changeTimeStamp,
    createdAt = createdAt
)
