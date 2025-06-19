package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.TransactionHistoryData

@Immutable
data class HistoryScreenData(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val expenses: List<TransactionHistoryData>
)