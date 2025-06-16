package ru.ttb220.presentation.model.screen

import ru.ttb220.presentation.model.IncomeResource

data class IncomesScreenResource(
    val incomes: List<IncomeResource>,
    val totalAmount: String,
)