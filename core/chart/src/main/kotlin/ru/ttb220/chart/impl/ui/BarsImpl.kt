package ru.ttb220.chart.impl.ui

import androidx.annotation.FloatRange
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val BAR_WIDTH = 6.dp
val BAR_ROUND_CORNER = 100.dp

@Composable
internal fun BarsImpl(
    maxValue: Float,
    values: List<Pair<Float, Color>>,
    modifier: Modifier = Modifier,
    barWidth: Dp = BAR_WIDTH,
    @FloatRange(from = 0.0, to = 1.0)
    alpha: Float = 1.0f
) {
    Canvas(
        modifier = modifier
    ) {
        val occupiedSpace = (barWidth * values.size).toPx()
        val freeSpace = size.width - occupiedSpace
        val gap = freeSpace / (values.size - 1)

        values.fold(0f) { startX, (value, color) ->
            val relativeValue = value / maxValue
            val barHeight = relativeValue * size.height

            drawRoundRect(
                color,
                topLeft = Offset(
                    startX,
                    size.height - barHeight
                ),
                size = Size(
                    barWidth.toPx(),
                    barHeight
                ),
                cornerRadius = CornerRadius(BAR_ROUND_CORNER.toPx()),
                style = Fill,
                alpha = alpha,
            )

            startX + barWidth.toPx() + gap
        }
    }
}

@Preview
@Composable
private fun BarsImplPreview() {
    val animation = rememberInfiniteTransition()
    val alpha by animation.animateFloat(
        0f,
        1f,
        infiniteRepeatable(
            tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    BarsImpl(
        maxValue = 100f,
        values = (10..100 step 2).map { it.toFloat() to Color.Black },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        alpha = alpha,
    )
}