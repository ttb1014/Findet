package ru.ttb220.presentation.model

import androidx.annotation.DrawableRes

data class ExpenseHistoryState(
    @DrawableRes val emojiId: Int? = null,
    val name: String,
    val description: String? = null,
    val amount: String,
    val time: String,
)
