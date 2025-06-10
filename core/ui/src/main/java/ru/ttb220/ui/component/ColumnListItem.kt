package ru.ttb220.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val DEFAULT_ICON_TINT = Color(0xFF3C3C43).copy(alpha = 0.3f)
private val DEFAULT_ICON_BACKGROUND = Color(0xFFD4FAE6)

// name.split(" ").map { it[0] }.take(2).joinToString("").uppercase()
sealed class LeadingIcon(
    val background: Color
) {
    class Emoji(
        @DrawableRes val emojiId: Int,
        background: Color = DEFAULT_ICON_BACKGROUND
    ) : LeadingIcon(background)

    class Letters(
        val letters: String,
        background: Color = DEFAULT_ICON_BACKGROUND
    ) : LeadingIcon(
        background
    )
}

/**
 * Wraps content height by default
 */
@Composable
fun ColumnListItem(
    title: String,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.surface,
    leadingIcon: LeadingIcon? = null,
    trailingText: String? = null,
    @DrawableRes trailingIcon: Int? = null,
    trailingIconTint: Color = DEFAULT_ICON_TINT,
    description: String? = null,
    trailingTextDescription: String? = null,
    shouldShowLeadingDivider: Boolean = false,
    shouldShowTrailingDivider: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(background),
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
            leadingIcon?.let {
                LeadingIcon(
                    leadingIcon = leadingIcon,
                    modifier = Modifier
                )

                Spacer(Modifier.width(16.dp))
            }

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

            trailingText?.let {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = trailingText,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.End,
                        softWrap = false,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    trailingTextDescription?.let {
                        Text(
                            text = trailingTextDescription,
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.End,
                            softWrap = false,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            trailingIcon?.let {
                Spacer(Modifier.width(16.dp))

                Icon(
                    painterResource(trailingIcon),
                    null,
                    tint = trailingIconTint
                )
            }
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
            modifier = modifier,
            iconBackground = leadingIcon.background
        )

        is LeadingIcon.Letters -> TextIcon(
            letters = leadingIcon.letters,
            modifier = modifier,
            iconBackground = leadingIcon.background
        )
    }
}

@Preview
@Composable
private fun ColumnListItemPreview() {
    ColumnListItem(
        title = "Всего",
        trailingText = "123123 P",
    )
}