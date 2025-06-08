package ru.ttb220.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockTotalExpenses

private val DEFAULT_TOTAL_AMOUNT_HEADER_FILL = Color(0xFFD4FAE6)

@Composable
fun TotalAmountHeader(
    expensesTotal: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(DEFAULT_TOTAL_AMOUNT_HEADER_FILL)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Всего",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Left,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = expensesTotal,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Left,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun TotalAmountHeaderPreview() {
    TotalAmountHeader(mockTotalExpenses)
}
