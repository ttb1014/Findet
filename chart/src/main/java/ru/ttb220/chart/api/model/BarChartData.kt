package ru.ttb220.chart.api.model

import androidx.compose.runtime.Immutable

@Immutable
data class BarChartData(
    val isAxisShown: Boolean,
    val barValues: List<Float>,
    val barColorResolver: BarColorResolver,
    val labels: List<String>,
)
