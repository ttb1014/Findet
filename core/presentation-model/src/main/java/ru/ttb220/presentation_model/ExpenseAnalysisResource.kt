package ru.ttb220.presentation_model

import androidx.annotation.DrawableRes

data class ExpenseAnalysisResource(
    // TODO: изменить способ хранения эмодзи
    @DrawableRes val emojiId: Int? = null,
    val name: String,
    val description: String? = null,
    val percentage: String,
    val amount: String
)