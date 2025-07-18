package ru.ttb220.designsystem.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color

fun Modifier.scrim(
    scrimColor: Color,
    alpha: Float = 0.32f,
) = this.drawWithContent {
    drawContent()
    drawRect(
        color = scrimColor,
        alpha = alpha
    )
}