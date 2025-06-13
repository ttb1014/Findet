package ru.ttb220.presentation_model

import androidx.annotation.DrawableRes

data class ExpenseHistoryResource(
    @DrawableRes val emojiId: Int? = null,
    val name: String,
    val description: String? = null,
    val amount: String,
    val time: String,
)
