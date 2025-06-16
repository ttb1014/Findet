package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.util.Emoji

@Immutable
data class ExpenseAnalysisState(
    val emojiId: Emoji? = null,
    val name: String,
    val description: String? = null,
    val percentage: String,
    val amount: String
)