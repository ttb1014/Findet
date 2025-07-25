package ru.ttb220.account.presentation.mock

import ru.ttb220.account.presentation.model.AccountScreenData
import ru.ttb220.account.presentation.model.ChartType
import ru.ttb220.account.presentation.model.defaultBarColorResolver
import ru.ttb220.chart.api.model.BarChartData
import ru.ttb220.presentation.model.CurrencyData
import ru.ttb220.presentation.model.R

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
    .map { it / 188f }
private val indices = listOf(10, 11, 18, 19, 20, 21, 22, 23)
private val xLabels = listOf("01.02", "14.01", "28.02")

internal val mockBarChartData = fills.mapIndexed { index, value ->
    value to if (index in indices) 1 else 0
} to xLabels

internal val mockAccountScreenData = AccountScreenData(
    accountName = "Сбербанк",
    leadingIconId = R.drawable.money_bag,
    balance = "-670 000 ₽",
    currencyData = CurrencyData.RUSSIAN_RUBLE,
    barChartData = BarChartData(
        isAxisShown = false,
        barValues = fills,
        barColorResolver = defaultBarColorResolver,
        labels = xLabels,
    ),
    chartType = ChartType.BAR
)