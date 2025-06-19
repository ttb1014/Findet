package ru.ttb220.expenses_history

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.mock.mockHistoryScreenData
import ru.ttb220.presentation.model.screen.HistoryScreenData
import ru.ttb220.presentation.model.util.Emoji
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.DynamicIcon
import ru.ttb220.presentation.ui.component.DynamicIconResource
import ru.ttb220.presentation.ui.component.ErrorBox
import ru.ttb220.presentation.ui.component.LoadingWheel
import ru.ttb220.presentation.ui.theme.GreenHighlight
import ru.ttb220.presentation.ui.theme.LightGreyIconTint

@Composable
fun ExpensesHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesHistoryViewModel = hiltViewModel()
) {
    val historyScreenState by viewModel.historyScreenState.collectAsStateWithLifecycle()

    when (historyScreenState) {
        is ExpensesHistoryScreenState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                message = (historyScreenState as ExpensesHistoryScreenState.Error).message,
                modifier = Modifier,
            )
        }

        is ExpensesHistoryScreenState.Loaded -> ExpensesHistoryScreenContent(
            historyScreenData = (historyScreenState as ExpensesHistoryScreenState.Loaded).data,
            modifier = modifier
        )

        ExpensesHistoryScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }
    }
}

@Composable
fun ExpensesHistoryScreenContent(
    historyScreenData: HistoryScreenData,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    Column(
        modifier.fillMaxSize()
    ) {
        TimeCard(
            R.string.start,
            historyScreenData.startDate
        )
        TimeCard(
            R.string.end,
            historyScreenData.endDate
        )
        ColumnListItem(
            title = stringResource(R.string.total),
            modifier = Modifier.height(56.dp),
            background = GreenHighlight,
            trailingText = historyScreenData.totalAmount,
        )

        val expenses = historyScreenData.expenses
        LazyColumn(
            state = lazyListState,
        ) {
            items(historyScreenData.expenses.size) { index ->

                val expense = expenses[index]

                val iconResource = expense.emoji?.let { emoji ->
                    DynamicIconResource.EmojiIconResource(
                        emoji = emoji,
                        background = GreenHighlight
                    )
                } ?: DynamicIconResource.TextIconResource(
                    text = expense.name.split(" ").take(2).map { it[0] }.joinToString("")
                        .uppercase()
                )

                // использовал другую перегрузку, т.к. отличается стиль текста в description
                HistoryColumnItem(
                    iconResource,
                    expense.name,
                    expense.description,
                    expense.amount,
                    expense.time
                )
            }
        }
    }
}

@Composable
private fun HistoryColumnItem(
    iconResource: DynamicIconResource,
    name: String,
    description: String?,
    amount: String,
    time: String,
    modifier: Modifier = Modifier
) {
    ColumnListItem(
        modifier = Modifier.height(70.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            DynamicIcon(
                dynamicIconResource = iconResource,
                Modifier.size(24.dp)
            )
        },
        trailingContent = @Composable {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = amount + "\n" + time,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(Modifier.width(16.dp))
            Icon(
                painter = painterResource(R.drawable.more_right),
                contentDescription = null,
                tint = LightGreyIconTint,
            )
        },
        centerContent = @Composable { modifierWeight ->
            Column(
                modifier = modifierWeight,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = name,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onSurface,
                    softWrap = false,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
                description?.let { description ->
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = description,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        softWrap = false,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun HistoryColumnItemPreview() {
    Column() {
        HistoryColumnItem(
            iconResource = DynamicIconResource.EmojiIconResource(
                emoji = Emoji.Text("\uD83D\uDC3B"),
            ),
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            amount = "100 000 $",
            time = "22:01",
            modifier = Modifier
        )
        HistoryColumnItem(
            iconResource = DynamicIconResource.EmojiIconResource(
                emoji = Emoji.Resource(R.drawable.doggy),
            ),
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            amount = "100 000 $",
            time = "22:01",
            modifier = Modifier
        )
    }
}

@Composable
fun TimeCard(
    @StringRes leadingText: Int,
    startDate: String,
    modifier: Modifier = Modifier,
) {
    ColumnListItem(
        title = stringResource(leadingText),
        modifier = modifier.height(56.dp),
        background = GreenHighlight,
        trailingText = startDate,
        shouldShowTrailingDivider = true,
    )
}

@Preview
@Composable
private fun ExpensesHistoryScreenContentPreview() {
    ExpensesHistoryScreenContent(
        mockHistoryScreenData
    )
}