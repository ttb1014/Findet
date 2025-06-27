package ru.ttb220.presentation.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation.ui.theme.FindetTheme

private const val DEFAULT_ROTATION_TIME = 5_000
private const val DEFAULT_WHEEL_WIDTH = 40f

@Composable
fun LoadingWheel(
    modifier: Modifier,
) {
    val color = MaterialTheme.colorScheme.onSurface

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val rotationAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = DEFAULT_ROTATION_TIME,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "AnimationPreview"
    )

    Canvas(
        modifier = modifier
            .padding(20.dp)
            .graphicsLayer { rotationZ = rotationAnimation * 2f }
    ) {
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = rotationAnimation,
            useCenter = false,
            style = Stroke(DEFAULT_WHEEL_WIDTH)
        )
    }
}

@Preview
@Composable
private fun LaodingWheelPreview() {
    FindetTheme {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }
    }
}