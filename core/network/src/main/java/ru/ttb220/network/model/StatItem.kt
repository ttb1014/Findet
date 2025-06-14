package ru.ttb220.network.model

import kotlinx.serialization.Serializable

@Serializable
data class StatItem(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String,
)
