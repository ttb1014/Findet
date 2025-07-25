package ru.ttb220.pin.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.pin.di.PinComponentProvider
import ru.ttb220.pin.presentation.ui.PinInputScreen
import ru.ttb220.pin.presentation.viewmodel.PinViewModel

const val ENTER_PIN_SCREEN_ROUTE_BASE = "$TOP_LEVEL_PIN_ROUTE/enter"

fun NavController.navigateToEnterPin(
    navOptions: NavOptions?
) {
    navigate(ENTER_PIN_SCREEN_ROUTE_BASE, navOptions)
}

fun NavGraphBuilder.enterPinScreen(
    navigateToContent: () -> Unit
) {
    composable(
        route = ENTER_PIN_SCREEN_ROUTE_BASE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as PinComponentProvider).providePinComponent().viewModelFactory
        val viewModel = viewModel<PinViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        PinInputScreen(
            viewModel = viewModel,
            onSuccess = navigateToContent
        )
    }
}