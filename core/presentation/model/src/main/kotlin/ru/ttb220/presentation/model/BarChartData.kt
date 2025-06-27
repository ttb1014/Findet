package ru.ttb220.presentation.model

import androidx.annotation.FloatRange
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class BarChartData(
    val bars: List<BarData>,
    val background: Color? = null,
    val xLabels: List<String>
) {
    @Immutable
    data class BarData(
        @FloatRange(from = 0.0, to = 1.0)
        val fill: Float,
        val color: Color,
        val width: Dp = 6.dp
    )
}