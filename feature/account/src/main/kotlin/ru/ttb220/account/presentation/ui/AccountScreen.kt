package ru.ttb220.account.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.account.presentation.mock.mockAccountScreenData
import ru.ttb220.account.presentation.state.AccountScreenState
import ru.ttb220.account.presentation.viewmodel.AccountViewModel
import ru.ttb220.chart.api.ui.BarChart
import ru.ttb220.designsystem.component.ColumnListItem
import ru.ttb220.designsystem.component.DynamicIconResource
import ru.ttb220.designsystem.component.ErrorBox
import ru.ttb220.designsystem.component.LoadingWheel
import ru.ttb220.presentation.model.CurrencyData
import ru.ttb220.presentation.model.EmojiData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.AccountScreenData

private val DEFAULT_LIST_ITEM_HEIGHT = 56.dp

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel,
    onBottomSheetShow: () -> Unit = {},
) {
    val accountScreenState by viewModel.accountScreenState.collectAsStateWithLifecycle()

    when (accountScreenState) {
        is AccountScreenState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                (accountScreenState as AccountScreenState.Error).message
            )
        }

        is AccountScreenState.ErrorResource -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                (accountScreenState as AccountScreenState.ErrorResource).messageId
            )
        }

        is AccountScreenState.Loaded -> {
            AccountScreenContent(
                accountScreenData = (accountScreenState as AccountScreenState.Loaded).data,
                modifier = modifier,
                onCurrencyClick = onBottomSheetShow
            )
        }

        AccountScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }
    }
}

@Composable
fun AccountScreenContent(
    accountScreenData: AccountScreenData,
    modifier: Modifier = Modifier,
    onCurrencyClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            BalanceItem(
                balance = accountScreenData.balance,
                leadingIconId = accountScreenData.leadingIconId,
            )
            CurrencyItem(
                accountScreenData.currencyData,
                Modifier.clickable(onClick = onCurrencyClick)
            )
        }
        // TODO: replace mock data
        BarChart(
            barChartData = ru.ttb220.chart.impl.mock.mockBarChartData,
            modifier = Modifier.height(233.dp)
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
        background = MaterialTheme.colorScheme.primaryContainer,
        dynamicIconResource = DynamicIconResource.EmojiIconResource(
            emojiData = EmojiData.Resource(leadingIconId),
            background = Color.White
        ),
        trailingIcon = R.drawable.more_right,
        shouldShowTrailingDivider = true,
    )
}

@Composable
fun CurrencyItem(
    currencyData: CurrencyData,
    modifier: Modifier = Modifier
) {
    ColumnListItem(
        title = "Валюта",
        trailingText = currencyData.symbol?.toString() ?: "",
        modifier = modifier.height(DEFAULT_LIST_ITEM_HEIGHT),
        background = MaterialTheme.colorScheme.primaryContainer,
        trailingIcon = R.drawable.more_right,
    )
}

@Preview
@Composable
private fun AccountScreenPreview() {
    AccountScreenContent(
        accountScreenData = mockAccountScreenData,
    )
}