package ru.ttb220.model

data class ExpenseResource(
    val emoji: String,
    val name: String,
    val shortDescription: String? = null,
    val amount: String,
)
