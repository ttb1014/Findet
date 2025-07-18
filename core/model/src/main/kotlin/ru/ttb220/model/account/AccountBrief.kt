package ru.ttb220.model.account

data class AccountBrief(
    val name: String,
    val balance: Double,
    val currency: String,
)

fun Account.toAccountBrief(): AccountBrief = AccountBrief(
    name = name,
    balance = balance,
    currency = currency
)