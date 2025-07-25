package ru.ttb220.chart.impl.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import ru.ttb220.chart.impl.mock.mockCircleDiagramData

val DEFAULT_WEIGHT = 8.dp

/**
 * @param modifier MUST specify size
 */
@Composable
fun CircleDiagramImpl(
    data: List<Pair<Int, Color>>,
    modifier: Modifier = Modifier,
    weight: Dp = DEFAULT_WEIGHT,
) {
    val maxValue = data.fold(0) { acc, (value, _) ->
        acc + value
    }
    Canvas(modifier) {
        val verticalOffset = weight.toPx()
        val zeroAngle = -90f
        data.fold(-90f) { acc, (value, color) ->
            val relativeValue = value.toFloat() / maxValue
            val sweepAngleBase = relativeValue * 360
            val sweepAngle = acc + sweepAngleBase
            drawArc(
                color = color,
                startAngle = acc,
                sweepAngle = sweepAngleBase,
                useCenter = false,
                topLeft = Offset(
                    weight.toPx() / 2,
                    weight.toPx() / 2
                ),
                size = Size(
                    width = size.width - weight.toPx(),
                    height = size.height - weight.toPx()
                ),
                style = Stroke(width = 8.dp.toPx())
            )

            sweepAngle
        }
    }
}

@Preview
@Composable
private fun CircleDiagramImplPreview() {
    CircleDiagramImpl(
        modifier = Modifier.size(DpSize(100.dp, 100.dp)),
        data = mockCircleDiagramData.data.map { it.second to it.third }
    )
}