package ru.ttb220.presentation.model.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

// Дефолтные эмодзи отличаются от того как они выглядят в фигме, поэтому сделал возможность хранить эмодзи
// и как строки и как ссылку на ресурс.
@Immutable
sealed class Emoji {
    @Immutable
    data class Text(
        val emojiString: String
    ) : Emoji()

    @Immutable
    data class Resource(
        @DrawableRes val emojiId: Int
    ) : Emoji()
}
