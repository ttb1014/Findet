package ru.ttb220.chart.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.ttb220.chart.impl.mock.labels

@Composable
internal fun LabelsImpl(
    labels: List<String>,
    labelsColor: Color,
    labelsTextStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        labels.forEach { label ->
            Text(
                text = label,
                color = labelsColor,
                softWrap = false,
                maxLines = 1,
                minLines = 1,
                style = labelsTextStyle
            )
        }
    }
}

@Preview
@Composable
private fun LabelsImplPreview() {
    LabelsImpl(
        labels = labels,
        labelsColor = Color.Black,
        labelsTextStyle = MaterialTheme.typography.labelSmall.copy(textAlign = TextAlign.Center),
        modifier = Modifier.fillMaxWidth()
    )
}