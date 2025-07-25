package ru.ttb220.chart.api.model

import androidx.compose.ui.graphics.Color

fun interface BarColorResolver {
    operator fun invoke(maxValue: Float, value: Float): Color
}