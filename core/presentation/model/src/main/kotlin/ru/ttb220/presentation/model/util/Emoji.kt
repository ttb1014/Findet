package ru.ttb220.presentation.model.util

import androidx.annotation.DrawableRes

// Дефолтные эмодзи отличаются от того как они выглядят в фигме, поэтому сделал возможность хранить эмодзи
// и как строки и как ссылку на ресурс.
sealed class Emoji {
    data class Text(
        val emojiString: String
    ) : Emoji()

    data class Resource(
        @DrawableRes val emojiId: Int
    ) : Emoji()
}
