package ru.ttb220.ui.component

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import ru.ttb220.mock.mockBarChartData
import ru.ttb220.ui.theme.Green
import ru.ttb220.ui.theme.Orange
import ru.ttb220.ui.theme.Roboto

data class BarChartResource(
    val bars: List<BarResource>,
    val background: Color? = null,
    val xLabels: List<String>
) {
    data class BarResource(
        @FloatRange(from = 0.0, to = 1.0)
        val fill: Float,
        val color: Color = Orange,
        val width: Dp = 6.dp
    )
}

@Composable
fun BarChart(
    barChartResource: BarChartResource,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(barChartResource.background ?: Color.Transparent)
            .padding(
                start = 16.dp,
                end = 16.dp
            )
    ) {
        // bars
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .drawWithCache {
                    onDrawBehind {
                        val spacerWidthPx = (size.width - barChartResource.bars
                            .fold(0.dp) { acc, barResource ->
                                acc + barResource.width
                            }
                            .toPx()
                                ) / (barChartResource.bars.size - 1)

                        barChartResource.bars.fold(0f) { offsetX, barResource ->
                            val barHeightPx = size.height * barResource.fill
                            val barWidthPx = barResource.width.toPx()
                            val offsetY = size.height - barHeightPx

                            // drawing bar
                            drawRoundRect(
                                color = barResource.color,
                                topLeft = Offset(
                                    offsetX,
                                    offsetY,
                                ),
                                size = Size(
                                    barWidthPx,
                                    barHeightPx,
                                ),
                                cornerRadius = CornerRadius(
                                    20f,
                                    20f
                                ),
                            )

                            // offset for the next bar
                            offsetX + barWidthPx + spacerWidthPx
                        }
                    }
                }
        )

        Spacer(Modifier.height(4.dp))

        // labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            barChartResource.xLabels.forEach { label ->
                XLabel(
                    label = label
                )
            }
        }
    }
}

@Composable
private fun XLabel(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        modifier = modifier.padding(horizontal = 6.dp),
        color = Color.Black.copy(alpha = 0.9f),
        textAlign = TextAlign.Center,
        softWrap = false,
        maxLines = 1,
        fontSize = 9.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontFamily = Roboto,
        letterSpacing = 0.sp,
        lineHeight = 1.em,
    )
}

@Preview
@Composable
private fun BarChartPreview() {
    BarChart(
        barChartResource = BarChartResource(
            bars = mockBarChartData.first
                .map { (fill, colorId) ->
                    BarChartResource.BarResource(
                        fill = fill,
                        color = if (colorId == 1) Green else Orange,
                    )
                },
            xLabels = mockBarChartData.second
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(233.dp)
    )
}