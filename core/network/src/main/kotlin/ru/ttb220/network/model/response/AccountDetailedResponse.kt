package ru.ttb220.network.model.response

import kotlinx.serialization.Serializable
import ru.ttb220.network.model.StatItemDto

@Serializable
data class AccountDetailedResponse(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatItemDto>,
    val expenseStats: List<StatItemDto>,
    val createdAt: String,
    val updatedAt: String,
)
