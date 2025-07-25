package ru.ttb220.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ttb220.designsystem.theme.FindetTheme
import ru.ttb220.designsystem.theme.Roboto
import ru.ttb220.presentation.model.EmojiData
import ru.ttb220.presentation.model.R

// Либо эмодзи либо текст
@Immutable
sealed class DynamicIconResource(
    val background: Color,
) {
    class EmojiIconResource(
        val emojiData: EmojiData,
        background: Color = Color.Unspecified,
    ) : DynamicIconResource(background)

    class TextIconResource(
        val text: String,
        background: Color = Color.Unspecified,
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
        is DynamicIconResource.EmojiIconResource -> EmojiIcon(
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
private fun EmojiIcon(
    emojiIconResource: DynamicIconResource.EmojiIconResource,
    modifier: Modifier = Modifier,
) {
    // Эмодзи сама по себе либо текстовая-дефолтная, либо из ресурсов (сделал, чтобы отображение совпадало с макетом)
    val backgroundColor =
        if (emojiIconResource.background == Color.Unspecified)
            MaterialTheme.colorScheme.primaryContainer else emojiIconResource.background
    when (emojiIconResource.emojiData) {
        is EmojiData.Resource -> Image(
            painter = painterResource(emojiIconResource.emojiData.emojiId),
            contentDescription = null,
            modifier = modifier
                .size(24.dp)
                .background(backgroundColor, CircleShape),
            alignment = Alignment.Center
        )

        // подогнано вручную
        is EmojiData.Text -> Text(
            text = emojiIconResource.emojiData.emojiString,
            modifier = Modifier
                .size(24.dp)
                .background(backgroundColor, RoundedCornerShape(100.dp)),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
            fontFamily = Roboto,
            letterSpacing = 0.sp,
            lineHeight = 24.sp,
            softWrap = false,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun EmojiIconPreview() {
    FindetTheme {
        Column() {
            EmojiIcon(
                emojiIconResource = DynamicIconResource.EmojiIconResource(
                    emojiData = EmojiData.Text("\uD83D\uDC3B"),
                    MaterialTheme.colorScheme.primaryContainer
                ),
            )
            EmojiIcon(
                emojiIconResource = DynamicIconResource.EmojiIconResource(
                    emojiData = EmojiData.Resource(R.drawable.doggy),
                    MaterialTheme.colorScheme.primaryContainer
                ),
            )
        }
    }
}

@Composable
private fun TextIcon(
    textIconResource: DynamicIconResource.TextIconResource,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (textIconResource.background == Color.Unspecified)
            MaterialTheme.colorScheme.primaryContainer else textIconResource.background
    Box(
        modifier = modifier
            .size(24.dp)
            .background(backgroundColor, CircleShape),
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