package ru.ttb220.expenses.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.expenses.presentation.component.DeleteExpenseButton
import ru.ttb220.expenses.presentation.component.EditExpenseForm
import ru.ttb220.expenses.presentation.viewmodel.EditExpenseViewModel

@Composable
fun EditExpenseScreen(
    viewModel: EditExpenseViewModel,
    modifier: Modifier = Modifier,
    // external callbacks
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {
        EditExpenseForm(
            state = state,
            onIntent = viewModel::onIntent,
            modifier = modifier,
            onAccountSelectorLaunch = onAccountSelectorLaunch,
            onCategorySelectorLaunch = onCategorySelectorLaunch
        )
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            DeleteExpenseButton(
                onClick = {
                    viewModel.onDeleteExpenseClick()
                    onDismiss()
                }
            )
        }
    }
}