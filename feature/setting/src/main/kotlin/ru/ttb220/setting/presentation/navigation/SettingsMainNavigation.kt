package ru.ttb220.setting.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.setting.di.SettingsComponentProvider
import ru.ttb220.setting.presentation.ui.SettingsScreen
import ru.ttb220.setting.presentation.viewmodel.SettingsViewModel

const val SETTINGS_MAIN_SCREEN_ROUTE_BASE = "$TOP_LEVEL_SETTINGS_ROUTE/main"

fun NavController.navigateToSettingsMain(
    navOptions: NavOptions? = null,
) = navigate(SETTINGS_MAIN_SCREEN_ROUTE_BASE, navOptions)

fun NavGraphBuilder.settingsMainScreen(
    navigateToSetupPin: () -> Unit = {},
    navigateToSyncFrequency: () -> Unit = {},
    navigateToInfo: () -> Unit = {},
) {
    composable(
        route = SETTINGS_MAIN_SCREEN_ROUTE_BASE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as SettingsComponentProvider).provideSettingsComponent().viewModelFactory
        val viewModel = viewModel<SettingsViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        SettingsScreen(
            viewModel,
            navigateToSetupPin = navigateToSetupPin,
            navigateToSyncFrequency = navigateToSyncFrequency,
            navigateToInfo = navigateToInfo
        )
    }
}