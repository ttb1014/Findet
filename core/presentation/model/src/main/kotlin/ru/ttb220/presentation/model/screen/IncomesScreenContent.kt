package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.IncomeState

@Immutable
data class IncomesScreenContent(
    val incomes: List<IncomeState>,
    val totalAmount: String,
)