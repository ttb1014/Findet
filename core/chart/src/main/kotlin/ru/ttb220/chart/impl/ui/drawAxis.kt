package ru.ttb220.chart.impl.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// full vibe code
fun DrawScope.drawAxis(
    axisColor: Color = Color.Black,
    labelsX: List<String> = emptyList(),
    labelsY: List<String> = emptyList(),
    paddingLeft: Float = 40f,
    paddingBottom: Float = 30f,
    labelColor: Color = Color.Black,
    labelTextSizePx: Float = 9.sp.toPx(),
) {
    // рисуем ось X (горизонтальную)
    val yAxisPos = size.height - paddingBottom
    drawLine(
        color = axisColor,
        start = Offset(paddingLeft, yAxisPos),
        end = Offset(size.width, yAxisPos),
        strokeWidth = 2f
    )

    // рисуем ось Y (вертикальную)
    drawLine(
        color = axisColor,
        start = Offset(paddingLeft, 0f),
        end = Offset(paddingLeft, yAxisPos),
        strokeWidth = 2f
    )

    val arrowSize = 12f // размер стрелки в пикселях
    // Стрелка на конце оси X (вправо)
    drawLine(
        color = axisColor,
        start = Offset(size.width, yAxisPos),
        end = Offset(size.width - arrowSize, yAxisPos - arrowSize / 2),
        strokeWidth = 2f
    )
    drawLine(
        color = axisColor,
        start = Offset(size.width, yAxisPos),
        end = Offset(size.width - arrowSize, yAxisPos + arrowSize / 2),
        strokeWidth = 2f
    )

    // Стрелка на конце оси Y (вверх)
    drawLine(
        color = axisColor,
        start = Offset(paddingLeft, 0f),
        end = Offset(paddingLeft - arrowSize / 2, arrowSize),
        strokeWidth = 2f
    )
    drawLine(
        color = axisColor,
        start = Offset(paddingLeft, 0f),
        end = Offset(paddingLeft + arrowSize / 2, arrowSize),
        strokeWidth = 2f
    )

    // рисуем метки на оси X (равномерно)
    if (labelsX.isNotEmpty()) {
        val stepX = (size.width - paddingLeft) / (labelsX.size - 1)
        labelsX.forEachIndexed { index, label ->
            val x = paddingLeft + stepX * index
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    label,
                    x,
                    size.height - 4f,
                    android.graphics.Paint().apply {
                        color = labelColor.toArgb()
                        textSize = labelTextSizePx
                        textAlign = android.graphics.Paint.Align.CENTER
                        isAntiAlias = true
                    }
                )
            }
        }
    }

    // рисуем метки на оси Y (равномерно)
    if (labelsY.isNotEmpty()) {
        val stepY = (yAxisPos) / (labelsY.size - 1)
        labelsY.forEachIndexed { index, label ->
            val y = yAxisPos - stepY * index
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    label,
                    paddingLeft - 8f,
                    y + labelTextSizePx / 2,
                    android.graphics.Paint().apply {
                        color = labelColor.toArgb()
                        textSize = labelTextSizePx
                        textAlign = android.graphics.Paint.Align.RIGHT
                        isAntiAlias = true
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun drawAxisPreview() {
    Canvas(
        modifier = Modifier
            .width(412.dp)
            .height(233.dp)
    ) {
        drawAxis(
            labelsX = listOf("01.07", "02.07", "03.07"),
            labelsY = listOf("0", "50", "100"),
            axisColor = Color.Gray,
            labelColor = Color.DarkGray
        )
    }
}
