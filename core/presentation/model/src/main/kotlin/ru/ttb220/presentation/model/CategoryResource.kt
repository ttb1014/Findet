package ru.ttb220.presentation.model

import ru.ttb220.presentation.model.util.Emoji

data class CategoryResource(
    val emoji: Emoji? = null,
    val name: String
)
