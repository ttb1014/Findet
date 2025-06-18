package ru.ttb220.expenses

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockExpensesHistoryScreenContent
import ru.ttb220.presentation.model.screen.ExpensesHistoryScreenContent
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.DynamicIconResource
import ru.ttb220.presentation.ui.theme.GreenHighlight

@Composable
fun ExpensesHistoryScreenContent(
    expensesHistoryScreenContent: ExpensesHistoryScreenContent,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    Column(
        modifier.fillMaxSize()
    ) {
        TimeCard(
            R.string.start,
            expensesHistoryScreenContent.startDate
        )
        TimeCard(
            R.string.end,
            expensesHistoryScreenContent.endDate
        )
        ColumnListItem(
            title = stringResource(R.string.total),
            modifier = Modifier.height(56.dp),
            background = GreenHighlight,
            trailingText = expensesHistoryScreenContent.totalAmount,
        )

        val expenses = expensesHistoryScreenContent.expenses
        LazyColumn(
            state = lazyListState,
        ) {
            items(expensesHistoryScreenContent.expenses.size) { index ->

                val expense = expenses[index]
                ColumnListItem(
                    title = expense.name,
                    modifier = Modifier.height(70.dp),
                    dynamicIconResource = expense.emoji?.let { emoji ->
                        DynamicIconResource.EmojiIconResource(
                            emoji = emoji,
                            background = GreenHighlight
                        )
                    } ?: DynamicIconResource.TextIconResource(
                        text = expense.name.split(" ").take(2).map { it[0] }.joinToString("")
                            .uppercase()
                    ),
                    trailingText = expense.amount,
                    trailingIcon = R.drawable.more_right,
                    description = expense.description,
                    trailingTextDescription = expense.time,
                    shouldShowTrailingDivider = true,
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
        mockExpensesHistoryScreenContent
    )
}