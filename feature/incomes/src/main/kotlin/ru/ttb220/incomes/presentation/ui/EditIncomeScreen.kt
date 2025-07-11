package ru.ttb220.incomes.presentation.ui

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
import ru.ttb220.incomes.presentation.component.DeleteIncomeButton
import ru.ttb220.incomes.presentation.component.EditIncomeForm
import ru.ttb220.incomes.presentation.viewmodel.EditIncomeViewModel

@Composable
fun EditIncomeScreen(
    viewModel: EditIncomeViewModel,
    modifier: Modifier = Modifier,
    // external callbacks
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {
        EditIncomeForm(
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
            DeleteIncomeButton(
                onClick = {
                    viewModel.onDeleteIncomeClick()
                    onDismiss()
                }
            )
        }
    }
}