package ru.ttb220.expenses.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.expenses.presentation.component.EditExpenseForm
import ru.ttb220.expenses.presentation.viewmodel.AddExpenseViewModel

@Composable
fun AddExpenseScreen(
    viewModel: AddExpenseViewModel,
    modifier: Modifier = Modifier,
    // external callbacks
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    EditExpenseForm(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = modifier.fillMaxSize(),
        onAccountSelectorLaunch = onAccountSelectorLaunch,
        onCategorySelectorLaunch = onCategorySelectorLaunch
    )
}