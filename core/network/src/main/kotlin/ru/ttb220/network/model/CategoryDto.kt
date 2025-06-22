package ru.ttb220.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.model.Category

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

fun CategoryDto.toCategory(): Category = Category(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)