package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.ExpenseData

@Immutable
data class ExpensesScreenData(
    val expenses: List<ExpenseData>,
    val totalAmount: String,
)