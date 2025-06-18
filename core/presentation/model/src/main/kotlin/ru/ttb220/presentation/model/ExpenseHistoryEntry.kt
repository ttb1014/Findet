package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.util.Emoji

@Immutable
data class ExpenseHistoryEntry(
    val emoji: Emoji? = null,
    val name: String,
    val description: String? = null,
    val amount: String,
    val time: String,
)
