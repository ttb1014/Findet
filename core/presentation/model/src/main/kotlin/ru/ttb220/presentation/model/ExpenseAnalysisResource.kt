package ru.ttb220.presentation.model

import ru.ttb220.presentation.model.util.Emoji

data class ExpenseAnalysisResource(
    val emojiId: Emoji? = null,
    val name: String,
    val description: String? = null,
    val percentage: String,
    val amount: String
)