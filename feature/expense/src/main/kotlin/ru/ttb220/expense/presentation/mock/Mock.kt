package ru.ttb220.expense.presentation.mock

import ru.ttb220.presentation.model.EmojiData
import ru.ttb220.presentation.model.ExpenseData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.TransactionHistoryData
import ru.ttb220.presentation.model.screen.ExpensesScreenData
import ru.ttb220.presentation.model.screen.HistoryScreenData

val mockHistoryScreenData = HistoryScreenData(
    startDate = "Февраль 2025",
    endDate = "23:41",
    totalAmount = "125 868 ₽",
    expenses = listOf(
        TransactionHistoryData(
            0,
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        TransactionHistoryData(
            0,
            emojiData = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        TransactionHistoryData(
            0,
            emojiData = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        TransactionHistoryData(
            0,
            emojiData = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        TransactionHistoryData(
            0,
            emojiData = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
    )
)

val mockExpensesScreenData = ExpensesScreenData(
    expenses = listOf(
        ExpenseData(
            0,
            emojiDataId = EmojiData.Resource(R.drawable.house_with_garden),
            name = "Аренда квартиры",
            amount = "100 000 ₽"
        ),
        ExpenseData(
            0,
            emojiDataId = EmojiData.Resource(R.drawable.dress),
            name = "Одежда",
            amount = "100 000 ₽"
        ),
        ExpenseData(
            0,
            emojiDataId = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку",
            shortDescription = "Джек",
            amount = "100 000 ₽"
        ),
        ExpenseData(
            0,
            emojiDataId = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку",
            shortDescription = "Энни",
            amount = "100 000 ₽"
        ),
        ExpenseData(
            0,
            name = "Ремонт квартиры",
            amount = "100 000 ₽",
        ),
        ExpenseData(
            0,
            emojiDataId = EmojiData.Resource(R.drawable.lollipop),
            name = "Продукты",
            amount = "100 000 ₽",
        ),
        ExpenseData(
            0,
            emojiDataId = EmojiData.Resource(R.drawable.deadlift),
            name = "Спортзал",
            amount = "100 000 ₽",
        ),
        ExpenseData(
            0,
            emojiDataId = EmojiData.Resource(R.drawable.medicine),
            name = "Медицина",
            amount = "100 000 ₽",
        ),
    ),
    totalAmount = "436 558 ₽"
)

