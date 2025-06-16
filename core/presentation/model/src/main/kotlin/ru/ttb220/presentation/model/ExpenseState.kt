package ru.ttb220.presentation.model

import ru.ttb220.presentation.model.util.Emoji

data class ExpenseState(
    val emojiId: Emoji? = null,
    val name: String,
    val shortDescription: String? = null,
    val amount: String,
)
