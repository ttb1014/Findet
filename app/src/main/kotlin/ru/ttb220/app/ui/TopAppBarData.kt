package ru.ttb220.app.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class TopAppBarData(
    @StringRes val textId: Int,
    @DrawableRes val leadingIconId: Int? = null,
    @DrawableRes val trailingIconId: Int? = null,
)