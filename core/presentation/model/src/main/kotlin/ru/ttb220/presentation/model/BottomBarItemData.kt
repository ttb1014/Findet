package ru.ttb220.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class BottomBarItemData(
    val route: String,
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int,
    val isSelected: Boolean,
)
