package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable

// TODO: в макете 3 состояния или 2 - светлая, темная, авто?
@Immutable
data class SettingsScreenData(
    val isDarkThemeEnabled: Boolean,
    // some other user data
)