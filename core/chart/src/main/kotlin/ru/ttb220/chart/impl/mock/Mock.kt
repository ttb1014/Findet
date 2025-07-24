package ru.ttb220.chart.impl.mock

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import ru.ttb220.chart.api.model.BarChartData
import ru.ttb220.chart.api.model.CircleDiagramData

val fills = listOf(9f, 93f, 24f, 45f, 69f, 24f, 24f, 188f, 56f, 106f, 14f, 14f, 56f, 24f, 137f, 24f, 24f, 40f, 14f, 14f, 14f, 14f, 14f, 14f, 24f, 24f, 24f, 24f)
val labels = listOf("01.02", "14.01", "28.02")
val mockBarChartData = BarChartData(
    barValues = fills,
    barColorResolver = { max, value ->
        Color.Black
    },
    isAxisShown = false,
    labels = labels
)

val mockCircleDiagramData = CircleDiagramData(
    data = listOf(
        Triple("Рестораны", 10, Color.Green),
        Triple("На собачку", 10, Color.Yellow),
        Triple("На собачку", 20, Color.Yellow),
        Triple("На собачку", 20, Color.Yellow),
        Triple("На собачку", 10, Color.Yellow),
        Triple("На собачку", 30, Color.Yellow),
    )
)