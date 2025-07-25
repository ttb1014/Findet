package ru.ttb220.chart.api.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.ttb220.chart.api.model.BarChartData
import ru.ttb220.chart.impl.animation.DefaultAnimationSpec
import ru.ttb220.chart.impl.mock.mockBarChartData
import ru.ttb220.chart.impl.ui.BarsImpl
import ru.ttb220.chart.impl.ui.LabelsImpl
import ru.ttb220.chart.impl.ui.drawAxis

@Composable
fun BarChart(
    barChartData: BarChartData,
    modifier: Modifier = Modifier,
) {
    // axis appearance animation
    val animatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(barChartData.isAxisShown) {
        animatable.animateTo(
            targetValue = when (barChartData.isAxisShown) {
                false -> 1f
                true -> 0f
            },
            animationSpec = DefaultAnimationSpec
        )
    }

    // bars appearance animation
    val animatedBars = remember {
        barChartData.barValues.map { Animatable(0f) }
    }
    LaunchedEffect(Unit) {
        animatedBars.forEachIndexed { index, animatable ->
            launch {
                animatable.animateTo(
                    barChartData.barValues[index],
                    animationSpec = DefaultAnimationSpec
                )
            }
        }
    }

    val maxValue = barChartData.barValues.max()
    var columnWidth by remember {
        mutableFloatStateOf(0f)
    }
    Column(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                columnWidth = coordinates.size.width.toFloat()
            },
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            BarsImpl(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 32.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .let {
                        if (barChartData.isAxisShown) it.drawBehind(
                            onDraw = {
                                drawAxis(
                                    paddingLeft = 0f,
                                    paddingBottom = -2.dp.toPx(),
                                    labelTextSizePx = 9.sp.toPx()
                                )
                            }
                        ) else
                            it
                    },
                maxValue = maxValue,
                values = animatedBars.map { animatable ->
                    val value = animatable.value
                    value to barChartData.barColorResolver(maxValue, value)
                },
            )
        }

        Spacer(Modifier.height(4.dp))

        // width animation
        val labelsContainerWidth = remember {
            Animatable(0f)
        }
        LaunchedEffect(columnWidth) {
            labelsContainerWidth.animateTo(
                targetValue = columnWidth,
                animationSpec = DefaultAnimationSpec
            )
        }

        val labelContainerWidthDp = with(LocalDensity.current) {
            labelsContainerWidth.value.dp
        }
        LabelsImpl(
            labels = barChartData.labels,
            labelsColor = MaterialTheme.colorScheme.onSurface,
            labelsTextStyle = MaterialTheme.typography.labelSmall.copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .width(labelContainerWidthDp)
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun BarChartPreview() {
    BarChart(
        barChartData = mockBarChartData,
        modifier = Modifier
            .width(412.dp)
            .height(233.dp)
    )
}