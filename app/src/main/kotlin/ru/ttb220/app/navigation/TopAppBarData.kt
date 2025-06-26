package ru.ttb220.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TopAppBarData(
    @StringRes val textId: Int,
    @DrawableRes val leadingIconId: Int? = null,
    @DrawableRes val trailingIconId: Int? = null,
)