package ru.ttb220.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatItemDto(
    @SerialName("categoryId")
    val categoryId: Int,

    @SerialName("categoryName")
    val categoryName: String,

    @SerialName("emoji")
    val emoji: String,

    @SerialName("amount")
    val amount: String,
)