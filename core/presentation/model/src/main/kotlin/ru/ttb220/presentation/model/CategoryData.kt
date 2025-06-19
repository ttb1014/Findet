package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.util.Emoji

@Immutable
data class CategoryData(
    val emoji: Emoji? = null,
    val name: String
)
