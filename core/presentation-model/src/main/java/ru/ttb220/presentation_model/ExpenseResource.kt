package ru.ttb220.presentation_model

import androidx.annotation.DrawableRes

// TODO: изменить способ хранения эмодзи
data class ExpenseResource(
    @DrawableRes val emojiId: Int? = null,
    val name: String,
    val shortDescription: String? = null,
    val amount: String,
)
