package ru.ttb220.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)