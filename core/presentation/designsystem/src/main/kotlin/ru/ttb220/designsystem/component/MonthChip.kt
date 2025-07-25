package ru.ttb220.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MonthChip(
    month: String,
    year: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(200.dp))
            .padding(vertical = 6.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$month $year",
            color = MaterialTheme.colorScheme.onSurface,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun MonthChip(
    monthWithYear: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(200.dp))
            .padding(vertical = 6.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = monthWithYear,
            color = MaterialTheme.colorScheme.onSurface,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun MonthChipPreview() {
    MonthChip(
        "январь",
        "2025"
    )
}