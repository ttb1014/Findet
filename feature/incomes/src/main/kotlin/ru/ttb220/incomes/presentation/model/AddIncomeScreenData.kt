package ru.ttb220.incomes.presentation.model

data class AddIncomeScreenData(
    val accountName: String,
    val categoryName: String,
    val amount: String,
    val currencySymbol: String,
    val date: String,
    val time: String,
    val comment: String? = null
)