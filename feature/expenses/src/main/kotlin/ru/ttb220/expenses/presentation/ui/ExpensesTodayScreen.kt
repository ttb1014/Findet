package ru.ttb220.expenses.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.expenses.presentation.model.ExpensesTodayScreenState
import ru.ttb220.expenses.presentation.viewmodel.ExpensesTodayViewModel
import ru.ttb220.mock.mockExpensesScreenData
import ru.ttb220.presentation.model.ExpenseData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.ExpensesScreenData
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.DynamicIconResource
import ru.ttb220.presentation.ui.component.ErrorBox
import ru.ttb220.presentation.ui.component.LoadingWheel
import ru.ttb220.presentation.ui.theme.GreenHighlight

@Composable
fun ExpensesTodayScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesTodayViewModel,
    navigateToEditExpense: (Int) -> Unit = {}
) {
    val expensesTodayScreenState: ExpensesTodayScreenState by viewModel.expensesScreenState.collectAsStateWithLifecycle()

    when (expensesTodayScreenState) {
        is ExpensesTodayScreenState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                message = (expensesTodayScreenState as ExpensesTodayScreenState.Error).message,
                modifier = Modifier,
            )
        }

        is ExpensesTodayScreenState.ErrorResource -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                messageId = (expensesTodayScreenState as ExpensesTodayScreenState.ErrorResource).messageId,
                modifier = Modifier,
            )
        }

        is ExpensesTodayScreenState.Loaded -> ExpensesTodayScreenContent(
            expensesScreenData = (expensesTodayScreenState as ExpensesTodayScreenState.Loaded).data,
            modifier = modifier,
            navigateToEditExpense = navigateToEditExpense
        )

        ExpensesTodayScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }
    }
}

@Composable
fun ExpensesTodayScreenContent(
    expensesScreenData: ExpensesScreenData,
    modifier: Modifier = Modifier,
    navigateToEditExpense: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TotalAmountHeader(expensesScreenData.totalAmount)
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(expensesScreenData.expenses.size) { index ->
                val expense = expensesScreenData.expenses[index]

                ExpenseColumnItem(
                    expense,
                    Modifier.clickable {
                        navigateToEditExpense(expense.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun TotalAmountHeader(
    expensesTotal: String,
    modifier: Modifier = Modifier,
) {
    ColumnListItem(
        title = "Всего",
        trailingText = expensesTotal,
        modifier = modifier.height(56.dp),
        background = GreenHighlight
    )
}

@Composable
private fun ExpenseColumnItem(
    expenseData: ExpenseData,
    modifier: Modifier = Modifier,
) {
    val dynamicIconResource = expenseData.emojiDataId
        // Рисуем либо эмодзи из ресурса
        ?.let { DynamicIconResource.EmojiIconResource(emojiData = it) }
    // либо первые две буквы названия
        ?: DynamicIconResource.TextIconResource(
            expenseData.name
                .split(" ")
                .map { it[0] }
                .take(2)
                .joinToString("")
                .uppercase()
        )

    ColumnListItem(
        dynamicIconResource = dynamicIconResource,
        title = expenseData.name,
        trailingText = expenseData.amount,
        trailingIcon = R.drawable.more_right,
        modifier = modifier.height(70.dp),
        description = expenseData.shortDescription,
        shouldShowTrailingDivider = true
    )
}

@Preview
@Composable
private fun ExpensesListPreview() {
    ExpensesTodayScreenContent(
        expensesScreenData = mockExpensesScreenData,
        modifier = Modifier,
    )
}