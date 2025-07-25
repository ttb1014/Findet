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
import ru.ttb220.chart.impl.ui.LabelsImpl
import ru.ttb220.chart.impl.ui.LinearImpl
import ru.ttb220.chart.impl.ui.drawAxis

@Composable
fun LinearChart(
    barChartData: BarChartData,
    modifier: Modifier = Modifier,
) {
    val animatableAxis = remember { Animatable(0f) }
    LaunchedEffect(barChartData.isAxisShown) {
        animatableAxis.animateTo(
            targetValue = if (barChartData.isAxisShown) 0f else 1f,
            animationSpec = DefaultAnimationSpec
        )
    }

    // Анимация значений графика
    val animatedValues = remember {
        barChartData.barValues.map { Animatable(0f) }
    }
    LaunchedEffect(Unit) {
        animatedValues.forEachIndexed { index, anim ->
            launch {
                anim.animateTo(
                    targetValue = barChartData.barValues[index],
                    animationSpec = DefaultAnimationSpec
                )
            }
        }
    }

    val maxValue = barChartData.barValues.maxOrNull() ?: 0f
    var containerWidth by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                containerWidth = coordinates.size.width.toFloat()
            },
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            LinearImpl(
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
                values = animatedValues.map { anim ->
                    val v = anim.value
                    v to barChartData.barColorResolver(maxValue, v)
                },
                strokeWidth = 4.dp,
            )
        }

        Spacer(Modifier.height(4.dp))

        val labelsWidthAnim = remember { Animatable(0f) }
        LaunchedEffect(containerWidth) {
            labelsWidthAnim.animateTo(
                targetValue = containerWidth,
                animationSpec = DefaultAnimationSpec
            )
        }

        val labelsWidthDp = with(LocalDensity.current) { labelsWidthAnim.value.toDp() }
        LabelsImpl(
            labels = barChartData.labels,
            labelsColor = MaterialTheme.colorScheme.onSurface,
            labelsTextStyle = MaterialTheme.typography.labelSmall.copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .width(labelsWidthDp)
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun LinearChartPreview() {
    LinearChart(
        barChartData = mockBarChartData,
        modifier = Modifier
            .width(412.dp)
            .height(233.dp)
    )
}