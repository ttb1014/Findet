package ru.ttb220.model.expense

data class ExpenseDetailedResource(
    val account: String,
    val article: String,
    val amount: String,
    val date: String,
    val time: String,
    val description: String,
)
