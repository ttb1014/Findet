package ru.ttb220.chart.api.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CircleDiagramData(
    // name, percentage, color
    val data: List<Triple<String, Int, Color>>
)