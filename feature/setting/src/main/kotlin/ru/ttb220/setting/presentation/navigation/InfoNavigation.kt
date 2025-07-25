package ru.ttb220.setting.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.setting.di.SettingsComponentProvider
import ru.ttb220.setting.presentation.ui.InfoScreen
import ru.ttb220.setting.presentation.viewmodel.SettingsViewModel

const val SETTINGS_INFO_SCREEN_ROUTE_BASE = "$TOP_LEVEL_SETTINGS_ROUTE/info"

fun NavController.navigateToSettingsInfo(
    navOptions: NavOptions? = null
) = navigate(SETTINGS_INFO_SCREEN_ROUTE_BASE, navOptions)

fun NavGraphBuilder.settingsInfoScreen() {
    composable(
        route = SETTINGS_INFO_SCREEN_ROUTE_BASE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as SettingsComponentProvider).provideSettingsComponent().viewModelFactory
        val viewModel = viewModel<SettingsViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        InfoScreen (
            viewModel,
        )
    }
}