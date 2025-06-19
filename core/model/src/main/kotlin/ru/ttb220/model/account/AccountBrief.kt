package ru.ttb220.model.account

data class AccountBrief(
    val name: String,
    val balance: String,
    val currency: String,
) {
    constructor(account: Account) : this(
        name = account.name,
        balance = account.balance,
        currency = account.currency,
    )
}