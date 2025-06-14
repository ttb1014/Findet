package ru.ttb220.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)