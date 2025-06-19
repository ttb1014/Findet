package ru.ttb220.expenses

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockExpensesHistoryScreenData
import ru.ttb220.presentation.model.screen.ExpensesHistoryScreenData
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.DynamicIcon
import ru.ttb220.presentation.ui.component.DynamicIconResource
import ru.ttb220.presentation.ui.theme.GreenHighlight
import ru.ttb220.presentation.ui.theme.LightGreyIconTint

@Composable
fun ExpensesHistoryScreenContent(
    expensesHistoryScreenData: ExpensesHistoryScreenData,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    Column(
        modifier.fillMaxSize()
    ) {
        TimeCard(
            R.string.start,
            expensesHistoryScreenData.startDate
        )
        TimeCard(
            R.string.end,
            expensesHistoryScreenData.endDate
        )
        ColumnListItem(
            title = stringResource(R.string.total),
            modifier = Modifier.height(56.dp),
            background = GreenHighlight,
            trailingText = expensesHistoryScreenData.totalAmount,
        )

        val expenses = expensesHistoryScreenData.expenses
        LazyColumn(
            state = lazyListState,
        ) {
            items(expensesHistoryScreenData.expenses.size) { index ->

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
                ColumnListItem(
                    modifier = Modifier.height(70.dp),
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
        mockExpensesHistoryScreenData
    )
}