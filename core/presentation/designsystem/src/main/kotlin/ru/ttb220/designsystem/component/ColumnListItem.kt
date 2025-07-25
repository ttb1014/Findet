package ru.ttb220.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

/**
 * Fills max height by default. Should explicitly specify via [modifier]
 */
@Composable
fun ColumnListItem(
    title: String,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.surface,
    dynamicIconResource: DynamicIconResource? = null,
    trailingText: String? = null,
    @DrawableRes trailingIcon: Int? = null,
    trailingIconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
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
            // Рисует либо эмодзи, либо первые 2 буквы названия
            dynamicIconResource?.let {
                DynamicIcon(
                    dynamicIconResource = dynamicIconResource,
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

@Preview
@Composable
private fun ColumnListItemPreview() {
    ColumnListItem(
        title = "Всего",
        trailingText = "123123 P",
        modifier = Modifier.height(56.dp)
    )
}