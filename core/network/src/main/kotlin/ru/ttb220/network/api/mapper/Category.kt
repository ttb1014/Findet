package ru.ttb220.network.api.mapper

import ru.ttb220.model.Category
import ru.ttb220.network.api.model.CategoryDto

fun CategoryDto.toCategory(): Category = Category(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)