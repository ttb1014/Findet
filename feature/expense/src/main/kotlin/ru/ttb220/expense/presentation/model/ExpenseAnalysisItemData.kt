package ru.ttb220.expense.presentation.model

import ru.ttb220.designsystem.component.DynamicIconResource

data class ExpenseAnalysisItemData(
    val leadingIcon: DynamicIconResource,
    val expenseName: String,
    val expenseDescription: String?=null,
    val expensePercentage: String,
    val expenseAmount: String,
)
