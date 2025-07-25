package ru.ttb220.account.presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import ru.ttb220.chart.api.model.BarChartData
import ru.ttb220.presentation.model.CurrencyData

@Immutable
data class AccountScreenData(
    val accountName: String,
    @DrawableRes val leadingIconId: Int,
    val balance: String,
    val currencyData: CurrencyData,
    val barChartData: BarChartData,
    val chartType: ChartType,
)