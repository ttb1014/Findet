package ru.ttb220.domain_model

data class Category(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean,
)
