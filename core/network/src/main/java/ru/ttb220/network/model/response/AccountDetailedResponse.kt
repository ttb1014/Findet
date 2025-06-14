package ru.ttb220.network.model.response

import kotlinx.serialization.Serializable
import ru.ttb220.network.model.StatItem

@Serializable
data class AccountDetailedResponse(
    val id: String,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatItem>,
    val statItemStats: List<StatItem>,
    val createdAt: String,
    val updatedAt: String,
)
