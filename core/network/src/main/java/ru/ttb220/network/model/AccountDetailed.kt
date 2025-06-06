package ru.ttb220.network.model

data class AccountDetailed(
    val id: String,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<Income>,
    val expenseStats: List<Expense>,
    val createdAt: String,
    val updatedAt: String,
)
