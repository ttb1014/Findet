package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.util.Emoji

@Immutable
data class ExpenseData(
    val emojiId: Emoji? = null,
    val name: String,
    val shortDescription: String? = null,
    val amount: String,
)
