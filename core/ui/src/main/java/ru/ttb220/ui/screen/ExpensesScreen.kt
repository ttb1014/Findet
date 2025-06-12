package ru.ttb220.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockExpensesScreenResource
import ru.ttb220.presentation_model.expense.ExpenseResource
import ru.ttb220.presentation_model.expense.ExpensesScreenResource
import ru.ttb220.ui.R
import ru.ttb220.ui.component.ColumnListItem
import ru.ttb220.ui.component.DynamicIconResource
import ru.ttb220.ui.theme.GreenHighlight

// TODO: Переделать на LazyColumn. Должен ли фиксироваться item с общей суммой?
@Composable
fun ExpensesScreen(
    expensesScreenResource: ExpensesScreenResource,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TotalAmountHeader(expensesScreenResource.totalAmount)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            expensesScreenResource.expenses.forEach {
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
    expenseResource: ExpenseResource,
    modifier: Modifier = Modifier,
) {
    val dynamicIconResource = expenseResource.emojiId
        // Рисуем либо эмодзи из ресурса
        ?.let { DynamicIconResource.EmojiIconResource(emojiId = it) }
        // либо первые две буквы названия
        ?: DynamicIconResource.TextIconResource(expenseResource.name
            .split(" ")
            .map { it[0] }
            .take(2)
            .joinToString("")
            .uppercase()
        )

    ColumnListItem(
        dynamicIconResource = dynamicIconResource,
        title = expenseResource.name,
        trailingText = expenseResource.amount,
        trailingIcon = R.drawable.more_right,
        modifier = modifier.height(70.dp),
        description = expenseResource.shortDescription,
        shouldShowTrailingDivider = true
    )
}

@Preview
@Composable
private fun ExpensesListPreview() {
    ExpensesScreen(
        expensesScreenResource = mockExpensesScreenResource,
        modifier = Modifier,
    )
}