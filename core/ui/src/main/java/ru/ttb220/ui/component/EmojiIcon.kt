package ru.ttb220.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

private val DEFAULT_ICON_BACKGROUND = Color(0xFFD4FAE6)

@Composable
fun EmojiIcon(
    @DrawableRes emojiId: Int,
    modifier: Modifier = Modifier,
    iconBackground: Color = DEFAULT_ICON_BACKGROUND,
) {
    Image(
        painter = painterResource(emojiId),
        contentDescription = null,
        modifier = modifier
            .size(24.dp)
            .background(iconBackground, CircleShape),
        alignment = Alignment.Center
    )
}