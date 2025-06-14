package ru.ttb220.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)
