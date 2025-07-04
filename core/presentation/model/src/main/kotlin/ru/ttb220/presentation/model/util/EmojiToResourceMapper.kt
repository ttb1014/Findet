package ru.ttb220.presentation.model.util

import ru.ttb220.presentation.model.EmojiData
import ru.ttb220.presentation.model.R

// Маппер стандартных эмодзи на эмодзи из ресурсов.
val EmojiToResourceMapper = mapOf<String, Int>(
    "💰" to R.drawable.money_bag,
    "🏠" to R.drawable.house_with_garden,
    "🍎" to R.drawable.lollipop,
    "👕" to R.drawable.dress,
    "🏥" to R.drawable.medicine,
    "🏋️" to R.drawable.deadlift,
    "🐾" to R.drawable.doggy,
)

object EmojiMapper {

    fun getEmojiData(emoji: String): EmojiData {
        val emojiResId = EmojiToResourceMapper[emoji]

        return emojiResId?.let {
            EmojiData.Resource(
                emojiId = emojiResId,
            )
        } ?: EmojiData.Text(
            emojiString = emoji,
        )
    }
}