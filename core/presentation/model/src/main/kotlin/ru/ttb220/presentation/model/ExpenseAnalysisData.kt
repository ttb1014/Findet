package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class ExpenseAnalysisData(
    val emojiDataId: EmojiData? = null,
    val name: String,
    val description: String? = null,
    val percentage: String,
    val amount: String
)