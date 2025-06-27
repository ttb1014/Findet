package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable
import ru.ttb220.model.transaction.TransactionDetailed

@Immutable
data class IncomeData(
    val title: String,
    val amount: String,
)

fun TransactionDetailed.toIncomeData(currency: String) = IncomeData(
    title = category.name,
    amount = "$amount $currency",
)