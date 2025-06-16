package ru.ttb220.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation.ui.theme.Green

@Composable
fun FloatingActionButtonAdd(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(56.dp)
            .background(Green, CircleShape)
            .drawWithContent {
                drawContent()

                val lineLength = 16.dp.toPx()
                val lineWidth = 1.87.dp.toPx()

                val centerX = size.width / 2
                val centerY = size.height / 2

                drawLine(
                    color = Color.White,
                    start = Offset(centerX - lineLength / 2, centerY),
                    end = Offset(centerX + lineLength / 2, centerY),
                    strokeWidth = lineWidth,
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = Color.White,
                    start = Offset(centerX, centerY - lineLength / 2),
                    end = Offset(centerX, centerY + lineLength / 2),
                    strokeWidth = lineWidth,
                    cap = StrokeCap.Round
                )
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    )
}

@Preview
@Composable
private fun ButtonCirclePreview() {
    FloatingActionButtonAdd()
}