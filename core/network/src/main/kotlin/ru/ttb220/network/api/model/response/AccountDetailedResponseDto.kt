package ru.ttb220.network.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.network.api.model.StatItemDto

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