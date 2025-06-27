package ru.ttb220.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

@Composable
fun Switch(
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(
                width = 52.dp,
                height = 32.dp
            )
            .background(
                MaterialTheme.colorScheme.surfaceContainerHighest,
                RoundedCornerShape(100.dp)
            )
            .border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(100.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        val color = MaterialTheme.colorScheme.outline

        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    onDrawBehind {
                        drawCircle(
                            color = color,
                            radius = 8.dp.toPx(),
                            center = Offset(
                                8.dp.toPx(),
                                size.height / 2
                            ),
                        )
                    }
                }
        )
    }
}
