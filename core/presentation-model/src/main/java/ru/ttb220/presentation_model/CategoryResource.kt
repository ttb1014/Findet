package ru.ttb220.presentation_model

import androidx.annotation.DrawableRes

data class CategoryResource(
    // TODO: Изменить способ хранения эмодзи
    @DrawableRes val emojiId: Int? = null,
    val name: String
)
