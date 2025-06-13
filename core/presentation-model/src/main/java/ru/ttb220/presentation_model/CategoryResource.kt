package ru.ttb220.presentation_model

import ru.ttb220.presentation_model.util.Emoji

data class CategoryResource(
    val emoji: Emoji? = null,
    val name: String
)
