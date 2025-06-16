package ru.ttb220.presentation.model.screen

import ru.ttb220.presentation.model.IncomeState

data class IncomesScreenState(
    val incomes: List<IncomeState>,
    val totalAmount: String,
)