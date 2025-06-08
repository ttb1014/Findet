package ru.ttb220.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ttb220.model.ExpenseResource
import ru.ttb220.ui.R
import ru.ttb220.ui.theme.Roboto

private val DEFAULT_ICON_TINT = Color(0xFF3C3C43).copy(alpha = 0.3f)

sealed interface LeadingIcon {
    data class Emoji(@DrawableRes val emojiId: Int) : LeadingIcon
    // name.split(" ").map { it[0] }.take(2).joinToString("").uppercase()
    data class Letters(val letters: String) : LeadingIcon
}

/**
 * implicitly sets height to 70
 */
@Composable
fun ColumnListItem(
    leadingIcon: LeadingIcon,
    title: String,
    trailingText: String,
    @DrawableRes trailingIcon: Int,
    modifier: Modifier = Modifier,
    description: String? = null,
    shouldShowLeadingDivider: Boolean = false,
    shouldShowTrailingDivider: Boolean = false,
) {
    Column(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (shouldShowLeadingDivider)
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Draws either an emoji or first two letters of the name uppercase
            LeadingIcon(
                leadingIcon = leadingIcon,
                modifier = Modifier
            )

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = title,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onSurface,
                    softWrap = false,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
                description?.let {
                    Text(
                        text = it,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        softWrap = false,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Text(
                text = trailingText,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                softWrap = false,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.width(16.dp))

            Icon(
                painterResource(trailingIcon),
                null,
                tint = DEFAULT_ICON_TINT
            )
        }

        if (shouldShowTrailingDivider)
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
            )
    }
}

@Composable
private fun LeadingIcon(
    leadingIcon: LeadingIcon,
    modifier: Modifier = Modifier,
) {
    when (leadingIcon) {
        is LeadingIcon.Emoji -> EmojiIcon(
            emojiId = leadingIcon.emojiId,
            modifier = modifier
        )

        is LeadingIcon.Letters -> TextIcon(
            leadingIcon.letters
        )
    }
}

@Composable
private fun TextIcon(
    letters: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(Color(0xFFD4FAE6), CircleShape),
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

@Composable
private fun EmojiIcon(
    @DrawableRes emojiId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(emojiId),
        contentDescription = null,
        modifier = modifier.size(24.dp),
        alignment = Alignment.Center
    )
}