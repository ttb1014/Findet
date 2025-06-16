package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class ExpenseDetailedState(
    val account: String,
    val article: String,
    val amount: String,
    val date: String,
    val time: String,
    val description: String,
)
