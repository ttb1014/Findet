@file:Suppress("Warnings")

package ru.ttb220.mock

import ru.ttb220.presentation.model.CategoryData
import ru.ttb220.presentation.model.CurrencyData
import ru.ttb220.presentation.model.EmojiData
import ru.ttb220.presentation.model.ExpenseAnalysisData
import ru.ttb220.presentation.model.ExpenseData
import ru.ttb220.presentation.model.ExpenseDetailedData
import ru.ttb220.presentation.model.IncomeData
import ru.ttb220.presentation.model.TransactionHistoryData
import ru.ttb220.presentation.model.screen.AccountScreenData
import ru.ttb220.presentation.model.screen.CategoriesScreenData
import ru.ttb220.presentation.model.screen.ExpensesAnalysisScreenData
import ru.ttb220.presentation.model.screen.ExpensesScreenData
import ru.ttb220.presentation.model.screen.HistoryScreenData
import ru.ttb220.presentation.model.screen.IncomesScreenData
import ru.ttb220.presentation.model.screen.SettingsScreenData

val mockActiveAccountId = 54

val mockIsDarkThemeEnabled = false

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

val mockIncomesScreenData = IncomesScreenData(
    incomes = listOf(
        IncomeData(
            title = "Зарплата",
            amount = "500 000 ₽"
        ),
        IncomeData(
            title = "Подработка",
            amount = "100 000 ₽"
        )
    ),
    totalAmount = "600 000 ₽"
)

val mockAccountScreenData = AccountScreenData(
    accountName = "Сбербанк",
    leadingIconId = R.drawable.money_bag,
    balance = "-670 000 ₽",
    currencyData = CurrencyData.RUSSIAN_RUBLE
)

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

val mockSettingsScreenContent = SettingsScreenData(
    isDarkThemeEnabled = false
)

val mockExpenseDetailedData = ExpenseDetailedData(
    account = "Сбербанк",
    article = "Ремонт",
    amount = "25 270 ₽",
    date = "25.02.2025",
    time = "23:41",
    description = "Ремонт - фурнитура для дверей"
)

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

val mockExpensesAnalysisScreenData = ExpensesAnalysisScreenData(
    startDate = "февраль 2025",
    endDate = "март 2025",
    amount = "125 868 ₽",
    expenses = listOf(
        ExpenseAnalysisData(
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            percentage = "20%",
            amount = "20 000 ₽",
        ),
        ExpenseAnalysisData(
            emojiDataId = EmojiData.Resource(R.drawable.doggy),
            name = "На собачку",
            percentage = "80%",
            amount = "80 000 ₽",
        ),
    )
)

private val fills = listOf(
    9f,
    93f,
    24f,
    45f,
    69f,
    24f,
    24f,
    188f,
    56f,
    106f,
    14f,
    14f,
    56f,
    24f,
    137f,
    24f,
    24f,
    40f,
    14f,
    14f,
    14f,
    14f,
    14f,
    14f,
    24f,
    24f,
    24f,
    24f
)
    .map { it / 220 }
private val indices = listOf(10, 11, 18, 19, 20, 21, 22, 23)
private val xLabels = listOf("01.02", "14.01", "28.02")

val mockBarChartData = fills.mapIndexed { index, value ->
    value to if (index in indices) 1 else 0
} to xLabels