package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class IncomeState(
    val title: String,
    val amount: String,
)