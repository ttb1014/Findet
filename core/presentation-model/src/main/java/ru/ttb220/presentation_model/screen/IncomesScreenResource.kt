package ru.ttb220.presentation_model.screen

import ru.ttb220.presentation_model.IncomeResource

data class IncomesScreenResource(
    val incomes: List<IncomeResource>,
    val totalAmount: String,
)