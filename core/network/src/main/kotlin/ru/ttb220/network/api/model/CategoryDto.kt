package ru.ttb220.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("emoji")
    val emoji: String,

    @SerialName("isIncome")
    val isIncome: Boolean
)