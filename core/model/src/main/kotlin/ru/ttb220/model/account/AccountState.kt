package ru.ttb220.model.account

data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
) {
    constructor(
        id: Int,
        accountBrief: AccountBrief
    ) : this(
        id = id,
        name = accountBrief.name,
        balance = accountBrief.balance,
        currency = accountBrief.currency
    )
}