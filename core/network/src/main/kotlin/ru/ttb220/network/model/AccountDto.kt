package ru.ttb220.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ttb220.model.account.AccountState

@Serializable
data class AccountDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("balance")
    val balance: String,

    @SerialName("currency")
    val currency: String,
)

fun AccountDto.toAccountState(): AccountState = AccountState(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
)
