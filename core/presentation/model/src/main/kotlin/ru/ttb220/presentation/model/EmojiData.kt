package ru.ttb220.presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

// either a text emoji or one from app's resources
@Immutable
sealed class EmojiData {
    @Immutable
    data class Text(
        val emojiString: String
    ) : EmojiData()

    @Immutable
    data class Resource(
        @DrawableRes val emojiId: Int
    ) : EmojiData()
}
