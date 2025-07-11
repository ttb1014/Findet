package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable
import ru.ttb220.model.transaction.TransactionDetailed

@Immutable
data class IncomeData(
    val id: Int,
    val title: String,
    val amount: String,
)

fun TransactionDetailed.toIncomeData(currency: String) = IncomeData(
    id = id,
    title = category.name,
    amount = "$amount $currency",
)