package ru.ttb220.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NavigationState(
    val route: String,
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int,
    val isSelected: Boolean,
)
