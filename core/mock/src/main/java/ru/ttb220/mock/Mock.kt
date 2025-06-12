package ru.ttb220.mock

import ru.ttb220.presentation_model.AccountScreenResource
import ru.ttb220.presentation_model.CategoryResource
import ru.ttb220.presentation_model.IncomeResource
import ru.ttb220.presentation_model.IncomesScreenResource
import ru.ttb220.presentation_model.SettingsScreenResource
import ru.ttb220.presentation_model.expense.ExpenseAnalysisResource
import ru.ttb220.presentation_model.expense.ExpenseDetailedResource
import ru.ttb220.presentation_model.expense.ExpenseHistoryResource
import ru.ttb220.presentation_model.expense.ExpenseResource
import ru.ttb220.presentation_model.expense.ExpensesAnalysisScreenResource
import ru.ttb220.presentation_model.expense.ExpensesHistoryScreenResource
import ru.ttb220.presentation_model.expense.ExpensesScreenResource
import ru.ttb220.presentation_model.util.Currency

val mockExpensesScreenResource = ExpensesScreenResource(
    expenses = listOf(
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
    ),
    totalAmount = "436 558 ₽"
)

val mockIncomesScreenResource = IncomesScreenResource(
    incomes = listOf(
        IncomeResource(
            title = "Зарплата",
            amount = "500 000 ₽"
        ),
        IncomeResource(
            title = "Подработка",
            amount = "100 000 ₽"
        )
    ),
    totalAmount = "600 000 ₽"
)

val mockAccountScreenResource = AccountScreenResource(
    leadingIconId = R.drawable.money_bag,
    balance = "-670 000 ₽",
    currency = Currency.RUSSIAN_RUBLE
)

val mockArticleScreenResource = listOf(
    CategoryResource(
        emojiId = R.drawable.house_with_garden,
        name = "Аренда квартиры",
    ),
    CategoryResource(
        emojiId = R.drawable.dress,
        name = "Одежда"
    ),
    CategoryResource(
        emojiId = R.drawable.doggy,
        name = "На собачку"
    ),
    CategoryResource(
        emojiId = R.drawable.doggy,
        name = "На собачку"
    ),
    CategoryResource(
        name = "Ремонт квартиры"
    ),
    CategoryResource(
        emojiId = R.drawable.lollipop,
        name = "Продукты"
    ),
    CategoryResource(
        emojiId = R.drawable.deadlift,
        name = "Спортзал"
    ),
    CategoryResource(
        emojiId = R.drawable.medicine,
        name = "Медицина"
    )
)

val mockSettingsScreenResource = SettingsScreenResource(
    isDarkThemeEnabled = false
)

val mockExpenseDetailedResource = ExpenseDetailedResource(
    account = "Сбербанк",
    article = "Ремонт",
    amount = "25 270 ₽",
    date = "25.02.2025",
    time = "23:41",
    description = "Ремонт - фурнитура для дверей"
)

val mockExpensesHistoryScreenResource = ExpensesHistoryScreenResource(
    startDate = "Февраль 2025",
    endDate = "23:41",
    totalAmount = "125 868 ₽",
    expenses = listOf(
        ExpenseHistoryResource(
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        ExpenseHistoryResource(
            emojiId = R.drawable.doggy,
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        ExpenseHistoryResource(
            emojiId = R.drawable.doggy,
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        ExpenseHistoryResource(
            emojiId = R.drawable.doggy,
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
        ExpenseHistoryResource(
            emojiId = R.drawable.doggy,
            name = "На собачку",
            amount = "100 000 ₽",
            time = "22:01"
        ),
    )
)

val mockExpensesAnalysisScreenResource = ExpensesAnalysisScreenResource(
    startDate = "февраль 2025",
    endDate = "март 2025",
    amount = "125 868 ₽",
    expenses = listOf(
        ExpenseAnalysisResource(
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            percentage = "20%",
            amount = "20 000 ₽",
        ),
        ExpenseAnalysisResource(
            emojiId = R.drawable.doggy,
            name = "На собачку",
            percentage = "80%",
            amount = "80 000 ₽",
        ),
    )
)

private val fills = listOf(9f, 93f, 24f, 45f, 69f, 24f, 24f, 188f, 56f, 106f, 14f, 14f, 56f, 24f, 137f, 24f, 24f, 40f, 14f, 14f, 14f, 14f, 14f, 14f, 24f, 24f, 24f, 24f)
    .map { it / 220 }
private val indices = listOf(10, 11, 18, 19, 20, 21, 22, 23)
private val xLabels = listOf("01.02", "14.01", "28.02")

val mockBarChartData = fills.mapIndexed { index, value ->
    value to if (index in indices) 1 else 0
} to xLabels