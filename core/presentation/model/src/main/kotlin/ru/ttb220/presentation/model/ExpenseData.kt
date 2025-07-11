package ru.ttb220.presentation.model

import androidx.compose.runtime.Immutable
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.presentation.util.EmojiToResourceMapper

@Immutable
data class ExpenseData(
    val emojiDataId: EmojiData? = null,
    val name: String,
    val shortDescription: String? = null,
    val amount: String,
)

fun TransactionDetailed.toExpenseState(currencySymbol: String) = ExpenseData(
    emojiDataId = category.emoji.let emoji@{ emojiString ->
        val emojiRes = EmojiToResourceMapper[emojiString]
        emojiRes?.let { res ->
            return@emoji EmojiData.Resource(res)
        }

        EmojiData.Text(emojiString)
    },
    name = category.name,
    shortDescription = comment,
    amount = "$amount $currencySymbol",
)