package ru.ttb220.setting.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.setting.di.SettingsComponentProvider
import ru.ttb220.setting.presentation.mock.mockSettingsScreenContent
import ru.ttb220.setting.presentation.ui.SettingsScreen
import ru.ttb220.setting.presentation.viewmodel.SettingsViewModel

const val TOP_LEVEL_SETTINGS_ROUTE = "settings"

fun NavController.navigateToSettings(
    navOptions: NavOptions? = null
) = navigate(TOP_LEVEL_SETTINGS_ROUTE, navOptions)

fun NavGraphBuilder.settingsScreen() {
    composable(
        route = TOP_LEVEL_SETTINGS_ROUTE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as SettingsComponentProvider).provideSettingsComponent().viewModelFactory
        val viewModel = viewModel<SettingsViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        SettingsScreen(
            viewModel
        )
    }
}