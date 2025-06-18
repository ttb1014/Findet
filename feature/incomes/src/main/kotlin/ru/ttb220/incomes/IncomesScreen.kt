package ru.ttb220.incomes

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
import ru.ttb220.mock.mockIncomesScreenContent
import ru.ttb220.presentation.model.IncomeState
import ru.ttb220.presentation.model.screen.IncomesScreenContent
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.ErrorDialog
import ru.ttb220.presentation.ui.component.LoadingWheel
import ru.ttb220.presentation.ui.theme.GreenHighlight

@Composable
fun IncomesScreen(
    modifier: Modifier = Modifier,
    viewModel: IncomesVewModel = hiltViewModel(),
) {
    val incomesScreenState: IncomesScreenState by viewModel.incomesScreenState.collectAsStateWithLifecycle()

    when (incomesScreenState) {
        is IncomesScreenState.Error -> ErrorDialog(
            message = (incomesScreenState as IncomesScreenState.Error).message,
            modifier = Modifier,
        )

        is IncomesScreenState.Loaded -> IncomesScreenContent(
            incomesScreenContent = (incomesScreenState as IncomesScreenState.Loaded).data,
            modifier = modifier
        )

        IncomesScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }

        is IncomesScreenState.ErrorResource -> ErrorDialog(
            messageId = (incomesScreenState as IncomesScreenState.ErrorResource).messageId,
            modifier = Modifier,
        )
    }
}

@Composable
fun IncomesScreenContent(
    incomesScreenContent: IncomesScreenContent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TotalAmountHeader(incomesScreenContent.totalAmount)
        incomesScreenContent.incomes.forEachIndexed { index: Int, incomeState: IncomeState ->
            ColumnListItem(
                title = incomeState.title,
                trailingText = incomeState.amount,
                modifier = Modifier.height(73.dp),
                trailingIcon = R.drawable.more_right,
                shouldShowLeadingDivider = index == 0,
                shouldShowTrailingDivider = true
            )
        }
    }
}

@Composable
private fun TotalAmountHeader(
    totalAmount: String,
    modifier: Modifier = Modifier
) {
    ColumnListItem(
        title = "Всего",
        trailingText = totalAmount,
        modifier = modifier.height(56.dp),
        background = GreenHighlight
    )
}

@Preview
@Composable
private fun IncomesScreenPreview() {
    IncomesScreenContent(
        incomesScreenContent = mockIncomesScreenContent,
    )
}