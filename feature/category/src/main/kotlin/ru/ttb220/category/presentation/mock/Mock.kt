package ru.ttb220.category.presentation.mock

import ru.ttb220.presentation.model.CategoryData
import ru.ttb220.presentation.model.EmojiData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.CategoriesScreenData

val mockCategoriesScreenContent = CategoriesScreenData(
    data = listOf(
        CategoryData(
            emojiData = EmojiData.Resource(R.drawable.house_with_garden),
            name = "Аренда квартиры",
        ),
        CategoryData(
            emojiData = EmojiData.Resource(R.drawable.dress),
            name = "Одежда"
        ),
        CategoryData(
            emojiData = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку"
        ),
        CategoryData(
            emojiData = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку"
        ),
        CategoryData(
            name = "Ремонт квартиры"
        ),
        CategoryData(
            emojiData = EmojiData.Resource(R.drawable.lollipop),
            name = "Продукты"
        ),
        CategoryData(
            emojiData = EmojiData.Resource(R.drawable.deadlift),
            name = "Спортзал"
        ),
        CategoryData(
            emojiData = EmojiData.Resource(R.drawable.medicine),
            name = "Медицина"
        )
    )
)