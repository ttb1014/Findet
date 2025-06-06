package ru.ttb220.network.model

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String,
)
