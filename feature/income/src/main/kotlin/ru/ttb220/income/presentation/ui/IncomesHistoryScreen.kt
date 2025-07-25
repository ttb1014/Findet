package ru.ttb220.income.presentation.ui

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
import ru.ttb220.designsystem.component.ColumnListItem
import ru.ttb220.designsystem.component.DatePickerDialog
import ru.ttb220.designsystem.component.DynamicIcon
import ru.ttb220.designsystem.component.DynamicIconResource
import ru.ttb220.designsystem.component.ErrorBox
import ru.ttb220.designsystem.component.LoadingWheel
import ru.ttb220.designsystem.component.ThreeComponentListItem


import ru.ttb220.income.presentation.mock.mockHistoryScreenData
import ru.ttb220.income.presentation.model.AlertDatePickerState
import ru.ttb220.income.presentation.model.IncomesHistoryScreenState
import ru.ttb220.income.presentation.viewmodel.IncomesHistoryViewModel
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.HistoryScreenData

@Composable
fun IncomesHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: IncomesHistoryViewModel,
    navigateToEditIncome: (Int) -> Unit = {},
) {
    val historyScreenState by viewModel.historyScreenState.collectAsStateWithLifecycle()

    when (historyScreenState) {
        is IncomesHistoryScreenState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                message = (historyScreenState as IncomesHistoryScreenState.Error).message,
                modifier = Modifier,
            )
        }

        is IncomesHistoryScreenState.Loaded -> IncomesHistoryScreenContent(
            historyScreenData = (historyScreenState as IncomesHistoryScreenState.Loaded).data,
            modifier = modifier,
            onStartDateSelected = viewModel::onStartDateSelected,
            onEndDateSelected = viewModel::onEndDateSelected,
            navigateToEditIncome = navigateToEditIncome
        )

        IncomesHistoryScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }

        is IncomesHistoryScreenState.ErrorResource -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                messageId = (historyScreenState as IncomesHistoryScreenState.ErrorResource).messageId,
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun IncomesHistoryScreenContent(
    historyScreenData: HistoryScreenData,
    modifier: Modifier = Modifier,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {},
    navigateToEditIncome: (Int) -> Unit = {},
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
            R.string.start,
            historyScreenData.startDate,
            onClick = { alertDatePickerState = AlertDatePickerState.START_DATE }
        )
        TimeCard(
            R.string.end,
            historyScreenData.endDate,
            onClick = { alertDatePickerState = AlertDatePickerState.END_DATE }
        )
        ColumnListItem(
            title = stringResource(R.string.total),
            modifier = Modifier.height(56.dp),
            background = MaterialTheme.colorScheme.primaryContainer,
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
                        background = MaterialTheme.colorScheme.primaryContainer
                    )
                } ?: DynamicIconResource.TextIconResource(
                    text = expense.name.split(" ").take(2).map { it[0] }.joinToString("")
                        .uppercase()
                )

                // использовал другую перегрузку, т.к. отличается стиль текста в description
                ThreeComponentListItem(
                    modifier = Modifier
                        .height(70.dp)
                        .clickable {
                            navigateToEditIncome(expense.id)
                        },
                    shouldShowTrailingDivider = true,
                    leadingContent = @Composable {
                        DynamicIcon(
                            dynamicIconResource = iconResource,
                        )
                    },
                    trailingContent = @Composable {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End,
                        ) {
                            Text(
                                text = expense.amount + "\n" + expense.time,
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
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    centerContent = @Composable { modifierWeight ->
                        Column(
                            modifier = modifierWeight,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = expense.name,
                                modifier = Modifier,
                                color = MaterialTheme.colorScheme.onSurface,
                                softWrap = false,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            expense.description?.let { description ->
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
        }
    }
}

@Composable
fun TimeCard(
    @StringRes leadingText: Int,
    startDate: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) = {},
) {
    ColumnListItem(
        title = stringResource(leadingText),
        modifier = modifier
            .height(56.dp)
            .clickable { onClick() },
        background = MaterialTheme.colorScheme.primaryContainer,
        trailingText = startDate,
        shouldShowTrailingDivider = true,
    )
}

@Preview
@Composable
private fun ExpensesHistoryScreenContentPreview() {
    IncomesHistoryScreenContent(
        mockHistoryScreenData
    )
}