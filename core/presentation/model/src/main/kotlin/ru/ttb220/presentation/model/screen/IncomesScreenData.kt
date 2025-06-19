package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.IncomeData

@Immutable
data class IncomesScreenData(
    val incomes: List<IncomeData>,
    val totalAmount: String,
)