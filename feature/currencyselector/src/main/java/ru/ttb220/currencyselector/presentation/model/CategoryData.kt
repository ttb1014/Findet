package ru.ttb220.currencyselector.presentation.model

import ru.ttb220.model.Category
import ru.ttb220.presentation.ui.component.DynamicIconResource
import ru.ttb220.presentation.util.EmojiMapper

data class CategoryData(
    val categoryId: Int,
    val categoryName: String,
    val dynamicIconResource: DynamicIconResource
)

fun Category.toCategoryData() = CategoryData(
    categoryId = id,
    categoryName = name,
    dynamicIconResource = DynamicIconResource.EmojiIconResource(
        EmojiMapper.getEmojiData(emoji)
    )
)