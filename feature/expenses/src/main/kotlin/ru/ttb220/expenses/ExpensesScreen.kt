package ru.ttb220.expenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.mock.mockExpensesScreenData
import ru.ttb220.presentation.model.ExpenseData
import ru.ttb220.presentation.model.screen.ExpensesScreenData
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.DynamicIconResource
import ru.ttb220.presentation.ui.component.ErrorDialog
import ru.ttb220.presentation.ui.component.LoadingWheel
import ru.ttb220.presentation.ui.theme.GreenHighlight

@Composable
fun ExpensesScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel(),
) {
    val expensesScreenState: ExpensesScreenState by viewModel.expensesScreenState.collectAsStateWithLifecycle()

    when (expensesScreenState) {
        is ExpensesScreenState.Error -> ErrorDialog(
            message = (expensesScreenState as ExpensesScreenState.Error).message,
            modifier = Modifier,
        )

        is ExpensesScreenState.Loaded -> ExpensesScreenContent(
            expensesScreenData = (expensesScreenState as ExpensesScreenState.Loaded).data,
            modifier = modifier
        )

        ExpensesScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }

        is ExpensesScreenState.ErrorResource -> ErrorDialog(
            messageId = (expensesScreenState as ExpensesScreenState.ErrorResource).messageId,
            modifier = Modifier,
        )
    }
}

// TODO: Переделать на LazyColumn. Должен ли фиксироваться item с общей суммой?
@Composable
fun ExpensesScreenContent(
    expensesScreenData: ExpensesScreenData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TotalAmountHeader(expensesScreenData.totalAmount)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            expensesScreenData.expenses.forEach {
                ExpenseColumnItem(it)
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
    val dynamicIconResource = expenseData.emojiId
        // Рисуем либо эмодзи из ресурса
        ?.let { DynamicIconResource.EmojiIconResource(emoji = it) }
    // либо первые две буквы названия
        ?: DynamicIconResource.TextIconResource(expenseData.name
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
    ExpensesScreenContent(
        expensesScreenData = mockExpensesScreenData,
        modifier = Modifier,
    )
}

@Preview
@Composable
private fun ExpensesScreenPreview() {
    ExpensesScreen()
}