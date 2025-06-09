package ru.ttb220.model

import androidx.annotation.DrawableRes

data class ArticleResource(
    // TODO: Изменить способ хранения эмодзи
    @DrawableRes val emojiId: Int? = null,
    val name: String
)
