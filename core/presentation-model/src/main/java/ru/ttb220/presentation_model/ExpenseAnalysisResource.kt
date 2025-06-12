package ru.ttb220.presentation_model

import androidx.annotation.DrawableRes
import ru.ttb220.presentation_model.util.Emoji

data class ExpenseAnalysisResource(
    val emojiId: Emoji? = null,
    val name: String,
    val description: String? = null,
    val percentage: String,
    val amount: String
)