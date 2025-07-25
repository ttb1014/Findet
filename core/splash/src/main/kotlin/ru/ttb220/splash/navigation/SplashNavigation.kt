package ru.ttb220.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.splash.ui.SplashScreen

const val TOP_LEVEL_SPLASH_ROUTE = "splash"

fun NavController.navigateToSplash(
    navOptions: NavOptions? = null,
) = navigate(TOP_LEVEL_SPLASH_ROUTE, navOptions)

fun NavGraphBuilder.splashScreen(
    onNext: () -> Unit,
) {
    composable(
        route = TOP_LEVEL_SPLASH_ROUTE,
    ) {
        SplashScreen(
            onNext = onNext
        )
    }
}