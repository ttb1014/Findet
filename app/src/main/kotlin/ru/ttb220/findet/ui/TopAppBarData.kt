package ru.ttb220.findet.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import ru.ttb220.designsystem.theme.Green

@Immutable
data class TopAppBarData(
    @StringRes val textId: Int,
    @DrawableRes val leadingIconId: Int? = null,
    @DrawableRes val trailingIconId: Int? = null,
    val backgroundColor: Color = Green
)