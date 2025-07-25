package ru.ttb220.income.presentation.mock

import androidx.compose.ui.graphics.Color
import ru.ttb220.chart.api.model.CircleDiagramData
import ru.ttb220.presentation.model.EmojiData
import ru.ttb220.presentation.model.IncomeData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.TransactionHistoryData
import ru.ttb220.presentation.model.screen.HistoryScreenData
import ru.ttb220.presentation.model.screen.IncomesScreenData

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

val mockIncomesScreenData = IncomesScreenData(
    incomes = listOf(
        IncomeData(
            0,
            title = "Зарплата",
            amount = "500 000 ₽"
        ),
        IncomeData(
            0,
            title = "Подработка",
            amount = "100 000 ₽"
        )
    ),
    totalAmount = "600 000 ₽"
)

val mockCircleDiagramData = CircleDiagramData(
    data = listOf(
        Triple("Ремонт квартиры", 20, Color.Green),
        Triple("На собачку", 80, Color.Yellow)
    )
)