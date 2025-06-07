package ru.ttb220.model

// TODO: изменить способ хранения эмодзи
data class ExpenseResource(
    val emojiId: Int? = null,
    val name: String,
    val shortDescription: String? = null,
    val amount: String,
)
