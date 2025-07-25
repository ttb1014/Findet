package ru.ttb220.account.presentation.model

import androidx.compose.ui.graphics.Color
import ru.ttb220.chart.api.model.BarColorResolver

val defaultBarColorResolver = BarColorResolver { maxValue, value ->
    if (value < maxValue * 0.1f) Color(0xFF2AE881)
    else Color(0xFFFF5F00)
}
