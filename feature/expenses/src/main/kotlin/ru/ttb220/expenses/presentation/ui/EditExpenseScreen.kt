package ru.ttb220.expenses.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.expenses.presentation.component.EditExpenseForm
import ru.ttb220.expenses.presentation.viewmodel.EditExpenseViewModel

@Composable
fun EditExpenseScreen(
    viewModel: EditExpenseViewModel,
    modifier: Modifier = Modifier,
    // external callbacks
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    EditExpenseForm(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = modifier,
        onAccountSelectorLaunch = onAccountSelectorLaunch,
        onCategorySelectorLaunch = onCategorySelectorLaunch
    )
}