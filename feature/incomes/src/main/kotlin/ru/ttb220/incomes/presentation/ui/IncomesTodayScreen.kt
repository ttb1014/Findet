package ru.ttb220.incomes.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.incomes.presentation.model.IncomesTodayScreenState
import ru.ttb220.incomes.presentation.viewmodel.IncomesTodayViewModel
import ru.ttb220.mock.mockIncomesScreenData
import ru.ttb220.presentation.model.IncomeData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.IncomesScreenData
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.ErrorBox
import ru.ttb220.presentation.ui.component.LoadingWheel
import ru.ttb220.presentation.ui.theme.GreenHighlight

@Composable
fun IncomesTodayScreen(
    modifier: Modifier = Modifier,
    viewModel: IncomesTodayViewModel
) {
    val incomesTodayScreenState: IncomesTodayScreenState by viewModel.incomesScreenState.collectAsStateWithLifecycle()

    when (incomesTodayScreenState) {
        is IncomesTodayScreenState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                message = (incomesTodayScreenState as IncomesTodayScreenState.Error).message,
            )
        }

        is IncomesTodayScreenState.ErrorResource -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                messageId = (incomesTodayScreenState as IncomesTodayScreenState.ErrorResource).messageId,
            )
        }

        is IncomesTodayScreenState.Loaded -> IncomesTodayScreenContent(
            incomesScreenData = (incomesTodayScreenState as IncomesTodayScreenState.Loaded).data,
            modifier = modifier
        )

        IncomesTodayScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }
    }
}

@Composable
fun IncomesTodayScreenContent(
    incomesScreenData: IncomesScreenData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TotalAmountHeader(incomesScreenData.totalAmount)
        LazyColumn() {
            items(incomesScreenData.incomes.size) { index ->
                val incomeData = incomesScreenData.incomes[index]

                ColumnListItem(
                    title = incomeData.title,
                    trailingText = incomeData.amount,
                    modifier = Modifier.height(73.dp),
                    trailingIcon = R.drawable.more_right,
                    shouldShowLeadingDivider = index == 0,
                    shouldShowTrailingDivider = true
                )
            }
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
private fun IncomesTodayScreenPreview() {
    IncomesTodayScreenContent(
        incomesScreenData = mockIncomesScreenData,
    )
}