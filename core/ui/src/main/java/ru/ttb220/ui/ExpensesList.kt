package ru.ttb220.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ttb220.model.ExpenseResource

// TODO: Стоит переделать на LazyColumn.
@Composable
fun ExpensesList(
    expenses: List<ExpenseResource>,
    modifier: Modifier = Modifier
) {
    Column() {

    }
}

@Composable
fun ExpensesListHeader(
    expensesTotal: String,
    modifier: Modifier = Modifier
) {
    Row() {
        Text()
        Text()
    }
}