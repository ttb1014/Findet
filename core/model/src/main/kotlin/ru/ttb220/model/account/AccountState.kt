package ru.ttb220.model.account

data class AccountState(
    val id: Int,
    val name: String,
    val balance: Double,
    val currency: String,
)

fun Account.toAccountState(): AccountState = AccountState(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)