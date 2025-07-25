package ru.ttb220.chart.impl.ui

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun LinearImpl(
    maxValue: Float,
    values: List<Pair<Float, Color>>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp,
    alpha: Float = 1.0f,
) {
    Canvas(modifier = modifier) {
        if (values.size < 2) return@Canvas

        val barCount = values.size
        val widthPerStep = size.width / (barCount - 1)

        // Преобразуем значения в координаты по канве
        val points = values.mapIndexed { index, (value, _) ->
            val x = index * widthPerStep
            val y = size.height * (1f - (value / maxValue).coerceIn(0f, 1f))
            Offset(x, y)
        }

        // Рисуем плавную кривую (cubic Bezier)
        val path = Path().apply {
            moveTo(points[0].x, points[0].y)
            for (i in 0 until points.size - 1) {
                val p0 = points[i]
                val p1 = points[i + 1]
                val midX = (p0.x + p1.x) / 2
                quadraticBezierTo(p0.x, p0.y, midX, (p0.y + p1.y) / 2)
                quadraticBezierTo(midX, (p0.y + p1.y) / 2, p1.x, p1.y)
            }
        }

        // Рисуем фон под кривой
        drawPath(path = path, color = Color.LightGray.copy(alpha = 0.3f))

        // Рисуем линию кривой по path, меняя цвет для каждого сегмента
        for (i in 0 until points.size - 1) {
            val p0 = points[i]
            val p1 = points[i + 1]

            val segmentPath = Path().apply {
                moveTo(p0.x, p0.y)
                val midX = (p0.x + p1.x) / 2
                quadraticBezierTo(p0.x, p0.y, midX, (p0.y + p1.y) / 2)
                quadraticBezierTo(midX, (p0.y + p1.y) / 2, p1.x, p1.y)
            }

            drawPath(
                path = segmentPath,
                color = values[i].second,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round),
                alpha = alpha,
            )
        }

        // Рисуем точки на графике
        values.forEachIndexed { index, (value, color) ->
            val point = points[index]
            drawCircle(
                color = color,
                radius = strokeWidth.toPx(),
                center = point,
                alpha = alpha,
                style = Fill
            )
        }
    }
}

@Preview
@Composable
private fun LinearImplPreview() {
    val animation = rememberInfiniteTransition()
    val alpha by animation.animateFloat(
        0f,
        1f,
        infiniteRepeatable(
            tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    LinearImpl(
        maxValue = 100f,
        values = (10..100 step 2).map { it.toFloat() to Color.Black },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        alpha = alpha,
    )
}
