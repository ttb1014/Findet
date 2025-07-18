package ru.ttb220.setting.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.setting.presentation.mock.mockSettingsScreenContent
import ru.ttb220.setting.presentation.ui.SettingsScreen

const val TOP_LEVEL_SETTINGS_ROUTE = "settings"

fun NavController.navigateToSettings(
    navOptions: NavOptions? = null
) = navigate(TOP_LEVEL_SETTINGS_ROUTE, navOptions)

fun NavGraphBuilder.settingsScreen() {
    composable(
        route = TOP_LEVEL_SETTINGS_ROUTE,
    ) {
        SettingsScreen(
            mockSettingsScreenContent
        )
    }
}