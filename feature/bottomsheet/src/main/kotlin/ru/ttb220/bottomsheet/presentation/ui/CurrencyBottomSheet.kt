package ru.ttb220.bottomsheet.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.ttb220.bottomsheet.presentation.viewmodel.CurrencyViewModel
import ru.ttb220.designsystem.ThreeComponentListItem
import ru.ttb220.designsystem.theme.KeyError
import ru.ttb220.designsystem.theme.LightSurfaceContainerLow
import ru.ttb220.presentation.model.CurrencyData
import ru.ttb220.presentation.model.R
import kotlin.math.max
import kotlin.math.roundToInt

val DEFAULT_ITEM_HEIGHT = 72.dp
val DRAG_THRESHOLD = 100.dp

@Composable
fun CurrencyBottomSheet(
    currencies: List<CurrencyData> = CurrencyData.entries,
    modifier: Modifier = Modifier,
    viewModel: CurrencyViewModel,
    onClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    // Drag state
    var offsetY by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current

    // handle back gesture to dismiss
    BackHandler {
        onDismiss()
    }

    Column(
        modifier = modifier
            .offset {
                IntOffset(
                    x = 0,
                    y = max(0, offsetY.roundToInt())
                )
            }
            .pointerInput(onDismiss) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        val (x, y) = dragAmount
                        offsetY += y
                        // Prevent drag-up
                        offsetY = max(0f, offsetY)
                    },
                    onDragEnd = {
                        // If the offset after drag exceeds a threshold, dismiss
                        with(density) {
                            if (offsetY > DRAG_THRESHOLD.toPx()) {
                                onDismiss()
                                return@with
                            }

                            // Snap back
                            offsetY = 0f
                        }
                    },
                )
            }
            .background(
                LightSurfaceContainerLow,
                RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            )
            .windowInsetsPadding(
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal)
            )
    ) {
        Header()
        LazyColumn {
            items(currencies.size) { index ->
                CurrencySelectorItem(
                    currencies[index],
                    Modifier.clickable(onClick = {
                        viewModel.onCurrencyClick(currencies[index], onClick)
                    })
                )
            }
        }
        CancelItem(
            Modifier.clickable(onClick = onDismiss)
        )
    }
}

@Composable
private fun CancelItem(
    modifier: Modifier = Modifier
) {
    ThreeComponentListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(DEFAULT_ITEM_HEIGHT),
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
private fun CurrencySelectorItem(
    currencyData: CurrencyData,
    modifier: Modifier = Modifier,
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
                val handleWidth = 32.dp.toPx()
                val handleHeight = 4.dp.toPx()
                val x = (size.width - handleWidth) / 2
                val y = (size.height - handleHeight) / 2

                drawRoundRect(
                    handleColor,
                    topLeft = Offset(x, y),
                    size = Size(handleWidth, handleHeight),
                    cornerRadius = CornerRadius(100f, 100f),
                )
            }
        )
    }
}