package ru.ttb220.income.presentation.model

import ru.ttb220.designsystem.DynamicIconResource

data class IncomeAnalysisItemData(
    val leadingIcon: DynamicIconResource,
    val incomeName: String,
    val incomeDescription: String? = null,
    val incomePercentage: String,
    val incomeAmount: String,
)
