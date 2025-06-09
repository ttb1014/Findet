package ru.ttb220.ui.component

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ttb220.ui.theme.Roboto

private val DEFAULT_ICON_BACKGROUND = Color(0xFFD4FAE6)

@Composable
fun TextIcon(
    letters: String,
    modifier: Modifier = Modifier,
    iconBackground: Color = DEFAULT_ICON_BACKGROUND,
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(iconBackground, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letters,
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