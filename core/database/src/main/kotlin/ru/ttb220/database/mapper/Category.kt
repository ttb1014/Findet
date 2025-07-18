package ru.ttb220.database.mapper

import ru.ttb220.database.entity.CategoryEntity
import ru.ttb220.model.Category

fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)

fun CategoryEntity.toCategory(): Category = Category(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)