package ru.ttb220.incomes.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.incomes.presentation.component.EditIncomeForm
import ru.ttb220.incomes.presentation.viewmodel.AddIncomeViewModel

@Composable
fun AddIncomeScreen(
    viewModel: AddIncomeViewModel,
    modifier: Modifier = Modifier,
    // external callbacks
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    EditIncomeForm(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = modifier,
        onAccountSelectorLaunch = onAccountSelectorLaunch,
        onCategorySelectorLaunch = onCategorySelectorLaunch
    )
}