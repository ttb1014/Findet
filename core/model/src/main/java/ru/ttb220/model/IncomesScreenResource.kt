package ru.ttb220.model

data class IncomesScreenResource(
    val incomes: List<IncomeResource>,
    val totalAmount: String,
)