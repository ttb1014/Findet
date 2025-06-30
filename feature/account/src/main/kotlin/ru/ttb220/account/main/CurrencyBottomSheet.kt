package ru.ttb220.account.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation.model.CurrencyData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.ui.component.ThreeComponentListItem
import ru.ttb220.presentation.ui.theme.KeyError
import ru.ttb220.presentation.ui.theme.LightSurfaceContainerLow

val DEFAULT_ITEM_HEIGHT = 72.dp

@Composable
fun CurrencyBottomSheet(
    currencies: List<CurrencyData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                LightSurfaceContainerLow,
                RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            )
    ) {
        Header()
        LazyColumn {
            items(currencies.size) { index ->
                CurrencySelectorItem(currencies[index])
            }
        }
        CancelItem()
    }
}

@Composable
fun CancelItem(
    modifier: Modifier = Modifier
) {
    ThreeComponentListItem(
        modifier = modifier.fillMaxWidth().height(DEFAULT_ITEM_HEIGHT),
        background = KeyError,
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            Icon(
                painter = painterResource(R.drawable.cancel),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        centerContent = @Composable {
            Text(
                text = stringResource(R.string.cancel),
                color = MaterialTheme.colorScheme.onPrimary,
                softWrap = false,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                modifier = it
            )
        }
    )
}

@Composable
fun CurrencySelectorItem(
    currencyData: CurrencyData,
    modifier: Modifier = Modifier
) {
    ThreeComponentListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(DEFAULT_ITEM_HEIGHT),
        background = Color.Transparent,
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            Icon(
                painter = painterResource(currencyData.iconId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        centerContent = @Composable {
            val fullName = stringResource(currencyData.fullNameId)
            val contentText = currencyData.symbol?.let {
                "$fullName $it"
            } ?: fullName

            Text(
                text = contentText,
                style = MaterialTheme.typography.bodyLarge,
                softWrap = false,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = it
            )
        }
    )
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val handleColor = MaterialTheme.colorScheme.outline

        Box(
            modifier = Modifier.drawWithContent {
                drawContent()
                drawRoundRect(
                    handleColor,
                    size = Size(32.dp.toPx(), 4.dp.toPx()),
                    cornerRadius = CornerRadius(100f, 100f),
                )
            }
        )
    }
}

@Preview
@Composable
private fun CurrencyBottomSheetPreview() {
    CurrencyBottomSheet(CurrencyData.entries)
}