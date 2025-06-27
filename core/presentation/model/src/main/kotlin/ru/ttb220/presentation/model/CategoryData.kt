package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class CategoryData(
    val emojiData: EmojiData? = null,
    val name: String
)
