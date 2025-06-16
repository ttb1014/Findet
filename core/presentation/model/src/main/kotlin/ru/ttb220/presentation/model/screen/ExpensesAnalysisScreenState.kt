package ru.ttb220.presentation.model.screen

import ru.ttb220.presentation.model.ExpenseAnalysisState

class ExpensesAnalysisScreenState(
    val startDate: String,
    val endDate: String,
    val amount: String,
    val expenses: List<ExpenseAnalysisState>
)