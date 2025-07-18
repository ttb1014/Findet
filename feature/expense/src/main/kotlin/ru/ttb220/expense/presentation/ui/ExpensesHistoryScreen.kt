package ru.ttb220.expense.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.designsystem.ColumnListItem
import ru.ttb220.designsystem.DatePickerDialog
import ru.ttb220.designsystem.DynamicIcon
import ru.ttb220.designsystem.DynamicIconResource
import ru.ttb220.designsystem.ErrorBox
import ru.ttb220.designsystem.LoadingWheel
import ru.ttb220.designsystem.ThreeComponentListItem
import ru.ttb220.designsystem.theme.GreenHighlight
import ru.ttb220.designsystem.theme.LightGreyIconTint
import ru.ttb220.expense.presentation.mock.mockHistoryScreenData
import ru.ttb220.expense.presentation.model.AlertDatePickerState
import ru.ttb220.expense.presentation.model.ExpensesHistoryScreenState
import ru.ttb220.expense.presentation.viewmodel.ExpensesHistoryViewModel
import ru.ttb220.presentation.model.EmojiData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.HistoryScreenData

@Composable
fun ExpensesHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesHistoryViewModel,
    navigateToEditExpense: (Int) -> Unit = {}
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
            modifier = modifier,
            onStartDateSelected = viewModel::onStartDateSelected,
            onEndDateSelected = viewModel::onEndDateSelected,
            navigateToEditExpense = navigateToEditExpense
        )

        ExpensesHistoryScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }

        is ExpensesHistoryScreenState.ErrorResource -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                messageId = (historyScreenState as ExpensesHistoryScreenState.ErrorResource).messageId,
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun ExpensesHistoryScreenContent(
    historyScreenData: HistoryScreenData,
    modifier: Modifier = Modifier,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {},
    navigateToEditExpense: (Int) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    var alertDatePickerState by remember { mutableStateOf(AlertDatePickerState.HIDDEN) }

    if (alertDatePickerState != AlertDatePickerState.HIDDEN) {
        DatePickerDialog(
            onDismiss = {
                alertDatePickerState = AlertDatePickerState.HIDDEN
            },
            onDateSelected = {
                when (alertDatePickerState) {
                    AlertDatePickerState.START_DATE -> {
                        onStartDateSelected(it)
                    }

                    AlertDatePickerState.END_DATE -> {
                        onEndDateSelected(it)
                    }

                    AlertDatePickerState.HIDDEN -> {}
                }
            }
        )
    }

    Column(
        modifier.fillMaxSize()
    ) {
        TimeCard(
            leadingText = R.string.start,
            date = historyScreenData.startDate,
            onClick = { alertDatePickerState = AlertDatePickerState.START_DATE }
        )
        TimeCard(
            leadingText = R.string.end,
            date = historyScreenData.endDate,
            onClick = { alertDatePickerState = AlertDatePickerState.END_DATE }
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

                val iconResource = expense.emojiData?.let { emoji ->
                    DynamicIconResource.EmojiIconResource(
                        emojiData = emoji,
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
                    expense.time,
                    Modifier.clickable {
                        navigateToEditExpense(expense.id)
                    }
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
    ThreeComponentListItem(
        modifier = modifier.height(70.dp),
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
                emojiData = EmojiData.Text("\uD83D\uDC3B"),
            ),
            name = "Ремонт квартиры",
            description = "Ремонт - фурнитура для дверей",
            amount = "100 000 $",
            time = "22:01",
            modifier = Modifier
        )
        HistoryColumnItem(
            iconResource = DynamicIconResource.EmojiIconResource(
                emojiData = EmojiData.Resource(R.drawable.doggy),
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
    date: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) = {},
) {
    ColumnListItem(
        title = stringResource(leadingText),
        modifier = modifier
            .height(56.dp)
            .clickable { onClick() },
        background = GreenHighlight,
        trailingText = date,
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