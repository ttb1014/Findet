package ru.ttb220.model.expense

import androidx.annotation.DrawableRes

// TODO: изменить способ хранения эмодзи
data class ExpenseResource(
    @DrawableRes val emojiId: Int? = null,
    val name: String,
    val shortDescription: String? = null,
    val amount: String,
)
