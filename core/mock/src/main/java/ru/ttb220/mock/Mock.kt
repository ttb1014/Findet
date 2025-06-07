package ru.ttb220.mock

import ru.ttb220.model.ExpenseResource

val mockTotalExpenses = "436 558 ₽"

val mockExpenseResources = listOf(
    ExpenseResource(
        emojiId = R.drawable.house_with_garden,
        name = "Аренда квартиры",
        amount = "100 000 ₽"
    ),
    ExpenseResource(
        emojiId = R.drawable.dress,
        name = "Одежда",
        amount = "100 000 ₽"
    ),
    ExpenseResource(
        emojiId = R.drawable.doggy,
        name = "На собачку",
        shortDescription = "Джек",
        amount = "100 000 ₽"
    ),
    ExpenseResource(
        emojiId = R.drawable.doggy,
        name = "На собачку",
        shortDescription = "Энни",
        amount = "100 000 ₽"
    ),
    ExpenseResource(
        name = "Ремонт квартиры",
        amount = "100 000 ₽",
    ),
    ExpenseResource(
        emojiId = R.drawable.lollipop,
        name = "Продукты",
        amount = "100 000 ₽",
    ),
    ExpenseResource(
        emojiId = R.drawable.deadlift,
        name = "Спортзал",
        amount = "100 000 ₽",
    ),
    ExpenseResource(
        emojiId = R.drawable.medicine,
        name = "Медицина",
        amount = "100 000 ₽",
    ),
)