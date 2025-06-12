package ru.ttb220.presentation_model

import ru.ttb220.presentation_model.util.Emoji

data class ExpenseResource(
    val emojiId: Emoji? = null,
    val name: String,
    val shortDescription: String? = null,
    val amount: String,
)
