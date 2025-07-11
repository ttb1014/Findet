package ru.ttb220.account.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.account.di.AccountComponentProvider
import ru.ttb220.account.presentation.ui.AddAccountScreenContent
import ru.ttb220.account.presentation.viewmodel.AddAccountViewModel

const val ADD_ACCOUNT_SCREEN_ROUTE = "$TOP_LEVEL_ACCOUNT_ROUTE/add"

fun NavController.navigateToAddAccount(
    navOptions: NavOptions? = null
) {
    val route = ADD_ACCOUNT_SCREEN_ROUTE
    navigate(route, navOptions)
}

fun NavGraphBuilder.addAccountScreen() {
    composable(
        route = ADD_ACCOUNT_SCREEN_ROUTE,
    ) {
        val context = LocalContext.current.applicationContext
        val factory =
            (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
        val viewModel = viewModel<AddAccountViewModel>(factory = factory)

        AddAccountScreenContent(
            addAccountViewModel = viewModel
        )
    }
}