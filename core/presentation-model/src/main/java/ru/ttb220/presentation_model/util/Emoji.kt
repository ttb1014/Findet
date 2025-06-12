package ru.ttb220.presentation_model.util

import androidx.annotation.DrawableRes

sealed class Emoji {
    data class Text(
        val emojiString: String
    ) : Emoji()

    data class Resource(
        @DrawableRes val emojiId: Int
    ) : Emoji()
}
