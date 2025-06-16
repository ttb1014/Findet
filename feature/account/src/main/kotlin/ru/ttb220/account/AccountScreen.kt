package ru.ttb220.account

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockAccountScreenContent
import ru.ttb220.mock.mockBarChartData
import ru.ttb220.presentation.model.screen.AccountScreenState
import ru.ttb220.presentation.model.util.Currency
import ru.ttb220.presentation.model.util.Emoji
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.component.BarChart
import ru.ttb220.presentation.ui.component.BarChartResource
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.DynamicIconResource
import ru.ttb220.presentation.ui.theme.Green
import ru.ttb220.presentation.ui.theme.GreenHighlight
import ru.ttb220.presentation.ui.theme.Orange

private val DEFAULT_LIST_ITEM_HEIGHT = 56.dp

@Composable
fun AccountScreen(
    accountScreenState: AccountScreenState,
    modifier: Modifier = Modifier
) {
    Column {
        Column {
            BalanceItem(
                balance = accountScreenState.balance,
                leadingIconId = accountScreenState.leadingIconId,
            )
            CurrencyItem(accountScreenState.currency)
        }
        BarChart(
            barChartResource = BarChartResource(
                bars = mockBarChartData.first.map { barData ->
                    BarChartResource.BarResource(
                        fill = barData.first,
                        color = if (barData.second == 1) Green else Orange,
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
        modifier = modifier.height(DEFAULT_LIST_ITEM_HEIGHT),
        background = GreenHighlight,
        dynamicIconResource = DynamicIconResource.EmojiIconResource(
            emoji = Emoji.Resource(leadingIconId),
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
    ColumnListItem(
        title = "Валюта",
        trailingText = currency.symbol?.toString() ?: "",
        modifier = modifier.height(DEFAULT_LIST_ITEM_HEIGHT),
        background = GreenHighlight,
        trailingIcon = R.drawable.more_right,
    )
}

@Preview
@Composable
private fun AccountScreenPreview() {
    AccountScreen(
        accountScreenState = mockAccountScreenContent,
    )
}