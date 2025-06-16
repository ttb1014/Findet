package ru.ttb220.incomes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockIncomesScreenState
import ru.ttb220.presentation.model.IncomeState
import ru.ttb220.presentation.model.screen.IncomesScreenState
import ru.ttb220.ui.R
import ru.ttb220.ui.component.ColumnListItem
import ru.ttb220.ui.theme.GreenHighlight

@Composable
fun IncomesScreen(
    incomesScreenState: IncomesScreenState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TotalAmountHeader(incomesScreenState.totalAmount)
        incomesScreenState.incomes.forEachIndexed { index: Int, incomeState: IncomeState ->
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
    IncomesScreen(
        incomesScreenState = mockIncomesScreenState,
    )
}