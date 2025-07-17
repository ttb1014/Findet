package ru.ttb220.network.api.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.model.account.Account

@Serializable
data class AccountCreateRequestDto(
    @SerialName("name")
    val name: String,

    @SerialName("balance")
    val balance: String,

    @SerialName("currency")
    val currency: String,
)

fun Account.toAccountRequestDto(): AccountCreateRequestDto = AccountCreateRequestDto(
    name = name,
    balance = balance,
    currency = currency
)