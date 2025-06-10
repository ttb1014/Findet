package ru.ttb220.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockAccountScreenResource
import ru.ttb220.mock.mockBarChartData
import ru.ttb220.model.AccountScreenResource
import ru.ttb220.model.util.Currency
import ru.ttb220.ui.R
import ru.ttb220.ui.component.BarChart
import ru.ttb220.ui.component.BarChartResource
import ru.ttb220.ui.component.ColumnListItem
import ru.ttb220.ui.component.DEFAULT_BAR_COLOR1
import ru.ttb220.ui.component.DEFAULT_BAR_COLOR2
import ru.ttb220.ui.component.LeadingIcon

private val DEFAULT_BACKGROUND = Color(0xFFD4FAE6)

@Composable
fun AccountScreen(
    accountScreenResource: AccountScreenResource,
    modifier: Modifier = Modifier
) {
    Column() {
        Column() {
            BalanceItem(
                balance = accountScreenResource.balance,
                leadingIconId = accountScreenResource.leadingIconId,
            )
            CurrencyItem(accountScreenResource.currency)
        }
        BarChart(
            barChartResource = BarChartResource(
                bars = mockBarChartData.first.map { barData ->
                    BarChartResource.BarResource(
                        fill = barData.first,
                        color = if (barData.second == 1) DEFAULT_BAR_COLOR2 else DEFAULT_BAR_COLOR1,
                    )
                },
                xLabels = mockBarChartData.second
            ),
            modifier = modifier.height(233.dp)
        )
    }
}

@Composable
fun BalanceItem(
    balance: String,
    @DrawableRes leadingIconId: Int,
    modifier: Modifier = Modifier
) {
    ColumnListItem(
        title = "Баланс",
        trailingText = balance,
        modifier = modifier.height(56.dp),
        background = DEFAULT_BACKGROUND,
        leadingIcon = LeadingIcon.Emoji(
            emojiId = leadingIconId,
            background = Color.White
        ),
        trailingIcon = R.drawable.more_right,
        shouldShowTrailingDivider = true,
    )
}

@Composable
fun CurrencyItem(
    currency: Currency,
    modifier: Modifier = Modifier
) {
    // TODO: resolve trailing text
    ColumnListItem(
        title = "Валюта",
        trailingText = currency.symbol?.toString() ?: "",
        modifier = modifier.height(56.dp),
        background = DEFAULT_BACKGROUND,
        trailingIcon = R.drawable.more_right,
    )
}

@Preview
@Composable
private fun AccountScreenPreview() {
    AccountScreen(
        accountScreenResource = mockAccountScreenResource,
    )
}