package ru.ttb220.presentation.model

import android.icu.text.DecimalFormat
import androidx.compose.runtime.Immutable
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.presentation.model.util.EmojiToResourceMapper

@Immutable
data class TransactionHistoryData(
    val id: Int,
    val emojiData: EmojiData? = null,
    val name: String,
    val description: String? = null,
    val amount: String,
    val time: String,
)

private val DEFAULT_TIME_FORMAT = DecimalFormat("00")

fun TransactionDetailed.toTransactionHistoryData(
    currency: String,
    timeZone: TimeZone
): TransactionHistoryData {
    val emojiId = EmojiToResourceMapper[category.emoji]
    val emojiData = emojiId?.let {
        EmojiData.Resource(it)
    } ?: EmojiData.Text(category.emoji)

    val transactionTime = transactionDate
        .toLocalDateTime(timeZone)
        .run {
            DEFAULT_TIME_FORMAT.format(hour) +
                    ":" +
                    DEFAULT_TIME_FORMAT.format(minute)
        }

    return TransactionHistoryData(
        id = id,
        emojiData = emojiData,
        name = category.name,
        description = comment,
        amount = "$amount $currency",
        time = transactionTime
    )
}