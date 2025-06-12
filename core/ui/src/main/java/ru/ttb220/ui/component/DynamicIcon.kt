package ru.ttb220.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ttb220.ui.theme.GreenHighlight
import ru.ttb220.ui.theme.Roboto

sealed class DynamicIconResource(
    val background: Color
) {
    class EmojiIconResource(
        @DrawableRes val emojiId: Int,
        background: Color = GreenHighlight
    ) : DynamicIconResource(background)

    class TextIconResource(
        val text: String,
        background: Color = GreenHighlight
    ) : DynamicIconResource(
        background
    )
}

@Composable
fun DynamicIcon(
    dynamicIconResource: DynamicIconResource,
    modifier: Modifier = Modifier,
) {
    when (dynamicIconResource) {
        is DynamicIconResource.EmojiIconResource -> EmojiResourceIcon(
            emojiIconResource = dynamicIconResource,
            modifier = modifier,
        )

        is DynamicIconResource.TextIconResource -> TextIcon(
            textIconResource = dynamicIconResource,
            modifier = modifier,
        )
    }
}

@Composable
private fun EmojiResourceIcon(
    emojiIconResource: DynamicIconResource.EmojiIconResource,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(emojiIconResource.emojiId),
        contentDescription = null,
        modifier = modifier
            .size(24.dp)
            .background(emojiIconResource.background, CircleShape),
        alignment = Alignment.Center
    )
}

@Composable
private fun TextIcon(
    textIconResource: DynamicIconResource.TextIconResource,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(textIconResource.background, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = textIconResource.text,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 10.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
            fontFamily = Roboto,
            letterSpacing = 0.sp,
            lineHeight = 22.sp,
            softWrap = false,
            maxLines = 1,
        )
    }
}