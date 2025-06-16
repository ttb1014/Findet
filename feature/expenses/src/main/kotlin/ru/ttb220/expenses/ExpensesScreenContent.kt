package ru.ttb220.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockExpensesScreenContent
import ru.ttb220.presentation.model.ExpenseState
import ru.ttb220.presentation.model.screen.ExpensesScreenContent
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.DynamicIconResource
import ru.ttb220.presentation.ui.theme.GreenHighlight

@Composable
fun ExpensesScreen(
    expensesScreenState: ExpensesScreenState,
    modifier: Modifier = Modifier
) {
    TODO()
}

// TODO: Переделать на LazyColumn. Должен ли фиксироваться item с общей суммой?
@Composable
fun ExpensesScreenContent(
    expensesScreenContent: ExpensesScreenContent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TotalAmountHeader(expensesScreenContent.totalAmount)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            expensesScreenContent.expenses.forEach {
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
    expenseState: ExpenseState,
    modifier: Modifier = Modifier,
) {
    val dynamicIconResource = expenseState.emojiId
        // Рисуем либо эмодзи из ресурса
        ?.let { DynamicIconResource.EmojiIconResource(emoji = it) }
    // либо первые две буквы названия
        ?: DynamicIconResource.TextIconResource(expenseState.name
            .split(" ")
            .map { it[0] }
            .take(2)
            .joinToString("")
            .uppercase()
        )

    ColumnListItem(
        dynamicIconResource = dynamicIconResource,
        title = expenseState.name,
        trailingText = expenseState.amount,
        trailingIcon = R.drawable.more_right,
        modifier = modifier.height(70.dp),
        description = expenseState.shortDescription,
        shouldShowTrailingDivider = true
    )
}

@Preview
@Composable
private fun ExpensesListPreview() {
    ExpensesScreenContent(
        expensesScreenContent = mockExpensesScreenContent,
        modifier = Modifier,
    )
}