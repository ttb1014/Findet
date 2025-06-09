package ru.ttb220.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import ru.ttb220.ui.R
import ru.ttb220.ui.theme.Green

private val DEFAULT_TRAILING_ICON_TINT = Color(0xFF49454F)

/**
 * @param text Должен вмещаться в контейнер
 */
@Composable
fun TopAppBar(
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
) {
    Row(
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
            .background(Green)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let {
            TopAppBarIcon(
                leadingIcon,
                MaterialTheme.colorScheme.onSurface
            )
        } ?: Spacer(Modifier.size(48.dp))

        Text(
            text = text,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge
        )

        trailingIcon?.let {
            TopAppBarIcon(
                trailingIcon,
                DEFAULT_TRAILING_ICON_TINT
            )
        } ?: Spacer(Modifier.size(48.dp))
    }
}

@Composable
private fun TopAppBarIcon(
    @DrawableRes iconId: Int,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        // container
        Box(
            modifier = Modifier.padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier,
                tint = tint
            )
        }
    }
}

@Preview
@Composable
private fun TopAppbarPreview() {
    TopAppBar(
        text = "Мои расходы",
        leadingIcon = R.drawable.cross,
        trailingIcon = R.drawable.check,
        modifier = Modifier
    )
}