package ru.ttb220.chart.api.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import ru.ttb220.chart.api.model.CircleDiagramData
import ru.ttb220.chart.impl.mock.mockCircleDiagramData
import ru.ttb220.chart.impl.ui.CircleDiagramImpl
import ru.ttb220.chart.impl.ui.CircleDiagramLegendImpl
import ru.ttb220.chart.impl.ui.DEFAULT_WEIGHT
import kotlin.math.sqrt

@Composable
fun CircleDiagram(
    circleDiagramData: CircleDiagramData,
    modifier: Modifier = Modifier.size(150.dp),
) {
    var diagramContainerWidth by remember {
        mutableIntStateOf(0)
    }
    Box(
        modifier = modifier

    ) {
        CircleDiagramImpl(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    diagramContainerWidth = it.size.width
                },
            data = circleDiagramData.data.map { it.second to it.third }
        )

        // calculating inscribed square side length
        val density = LocalDensity.current
        val padding = remember(diagramContainerWidth, density) {
            val innerCircleDiameter = diagramContainerWidth -
                    with(density) { DEFAULT_WEIGHT.toPx() * 2 }

            val innerSquareSide = innerCircleDiameter / sqrt(2f)

            with(density) {
                ((diagramContainerWidth.toFloat() - innerSquareSide) / 2).toDp()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            CircleDiagramLegendImpl(
                data = circleDiagramData.data,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
private fun CircleDiagramPreview() {
    CircleDiagram(
        modifier = Modifier.size(DpSize(150.dp, 150.dp)),
        circleDiagramData = mockCircleDiagramData
    )
}