package ru.ttb220.chart.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleDiagramLegendImpl(
    data: List<Triple<String, Int, Color>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(data.size) { index ->
            val item = data[index]
            LegendItem(
                item.third,
                item.second,
                item.first,
            )
        }
    }
}

val LEGEND_ITEM_GAP = 3.23.dp
val LEGEND_INDICATOR_SIZE = 5.65.dp

@Composable
private fun LegendItem(
    color: Color,
    percentage: Int,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(
                    LEGEND_INDICATOR_SIZE
                )
                .background(color, CircleShape)
        )
        Spacer(Modifier.width(LEGEND_ITEM_GAP))
        Text(
            text = "$percentage% $title",
            color = MaterialTheme.colorScheme.onSurface,
            softWrap = false,
            maxLines = 1,
            minLines = 1,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
private fun LegendItemPreview() {
    LegendItem(
        MaterialTheme.colorScheme.primary,
        80,
        "Ремонт квартиры"
    )
}
