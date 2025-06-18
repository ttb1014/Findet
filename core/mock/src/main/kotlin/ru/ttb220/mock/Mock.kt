package ru.ttb220.mock

import ru.ttb220.presentation.model.CategoryState
import ru.ttb220.presentation.model.ExpenseAnalysisState
import ru.ttb220.presentation.model.ExpenseDetailedState
import ru.ttb220.presentation.model.ExpenseHistoryEntry
import ru.ttb220.presentation.model.ExpenseState
import ru.ttb220.presentation.model.IncomeState
import ru.ttb220.presentation.model.screen.AccountScreenState
import ru.ttb220.presentation.model.screen.ExpensesAnalysisScreenState
import ru.ttb220.presentation.model.screen.ExpensesHistoryScreenContent
import ru.ttb220.presentation.model.screen.ExpensesScreenContent
import ru.ttb220.presentation.model.screen.IncomesScreenState
import ru.ttb220.presentation.model.screen.SettingsScreenState
import ru.ttb220.presentation.model.util.Currency
import ru.ttb220.presentation.model.util.Emoji

val mockExpensesScreenContent = ExpensesScreenContent(
    expenses = listOf(
        ExpenseState(
            emojiId = Emoji.Resource(R.drawable.house_with_garden),
            name = "Аренда квартиры",
            amount = "100 000 ₽"
        ),
        ExpenseState(
            emojiId = Emoji.Resource(R.drawable.dress),
            name = "Одежда",
            amount = "100 000 ₽"
        ),
        ExpenseState(
            emojiId = Emoji.Resource(R.drawable.doggy),
            name = "На собачку",
            shortDescription = "Джек",
            amount = "100 000 ₽"
        ),
        ExpenseState(
            emojiId = Emoji.Resource(R.drawable.doggy),
            name = "На собачку",
            shortDescription = "Энни",
            amount = "100 000 ₽"
        ),
        ExpenseState(
            name = "Ремонт квартиры",
            amount = "100 000 ₽",
        ),
        ExpenseState(
            emojiId = Emoji.Resource(R.drawable.lollipop),
            name = "Продукты",
            amount = "100 000 ₽",
        ),
        ExpenseState(
            emojiId = Emoji.Resource(R.drawable.deadlift),
            name = "Спортзал",
            amount = "100 000 ₽",
        ),
        ExpenseState(
            emojiId = Emoji.Resource(R.drawable.medicine),
            name = "Медицина",
            amount = "100 000 ₽",
        ),
    ),
    totalAmount = "436 558 ₽"
)

val mockIncomesScreenContent = IncomesScreenState(
    incomes = listOf(
        IncomeState(
            title = "Зарплата",
            amount = "500 000 ₽"
        ),
        IncomeState(
            title = "Подработка",
            amount = "100 000 ₽"
        )
    ),
    totalAmount = "600 000 ₽"
)

val mockAccountScreenContent = AccountScreenState(
    leadingIconId = R.drawable.money_bag,
    balance = "-670 000 ₽",
    currency = Currency.RUSSIAN_RUBLE
)

val mockArticleScreenContent = listOf(
    CategoryState(
        emoji = Emoji.Resource(R.drawable.house_with_garden),
        name = "Аренда квартиры",
    ),
    CategoryState(
        emoji = Emoji.Resource(R.drawable.dress),
        name = "Одежда"
    ),
    CategoryState(
        emoji = Emoji.Resource(R.drawable.doggy),
        name = "На собачку"
    ),
    CategoryState(
        emoji = Emoji.Resource(R.drawable.doggy),
        name = "На собачку"
    ),
    CategoryState(
        name = "Ремонт квартиры"
    ),
    CategoryState(
        emoji = Emoji.Resource(R.drawable.lollipop),
        name = "Продукты"
    ),
    CategoryState(
        emoji = Emoji.Resource(R.drawable.deadlift),
        name = "Спортзал"
    ),
    CategoryState(
        emoji = Emoji.Resource(R.drawable.medicine),
        name = "Медицина"
    )
)

val mockSettingsScreenContent = SettingsScreenState(
    isDarkThemeEnabled = false
)

val mockExpenseDetailedState = ExpenseDetailedState(
    account = "Сбербанк",
    article = "Ремонт",
    amount = "25 270 ₽",
    date = "25.02.2025",
    time = "23:41",
    description = "Ремонт - фурнитура для дверей"
)

val mockExpensesHistoryScreenContent = ExpensesHistoryScreenContent(
    startDate = "Февраль 2025",
    endDate = "23:41",
    totalAmount = "125 868 ₽",
    expenses = listOf(
        ExpenseHistoryEntry(
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        ExpenseHistoryEntry(
            emoji = Emoji.Resource(R.drawable.doggy),
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        ExpenseHistoryEntry(
            emoji = Emoji.Resource(R.drawable.doggy),
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        ExpenseHistoryEntry(
            emoji = Emoji.Resource(R.drawable.doggy),
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        ExpenseHistoryEntry(
            emoji = Emoji.Resource(R.drawable.doggy),
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
    )
)

val mockExpensesAnalysisScreenState = ExpensesAnalysisScreenState(
    startDate = "февраль 2025",
    endDate = "март 2025",
    amount = "125 868 ₽",
    expenses = listOf(
        ExpenseAnalysisState(
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            percentage = "20%",
            amount = "20 000 ₽",
        ),
        ExpenseAnalysisState(
            emojiId = Emoji.Resource(R.drawable.doggy),
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