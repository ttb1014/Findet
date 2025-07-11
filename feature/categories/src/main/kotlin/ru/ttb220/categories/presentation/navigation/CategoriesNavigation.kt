package ru.ttb220.categories.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.categories.di.CategoriesComponentProvider
import ru.ttb220.categories.presentation.ui.CategoriesScreen
import ru.ttb220.categories.presentation.viewmodel.CategoriesViewModel

const val TOP_LEVEL_CATEGORIES_ROUTE = "categories"

fun NavController.navigateToCategories(
    navOptions: NavOptions? = null
) = navigate(TOP_LEVEL_CATEGORIES_ROUTE, navOptions)

fun NavGraphBuilder.categoriesScreen() {
    composable(
        route = TOP_LEVEL_CATEGORIES_ROUTE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as CategoriesComponentProvider).provideCategoriesComponent().viewModelFactory
        val viewModel = viewModel<CategoriesViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )


        CategoriesScreen(viewModel = viewModel)
    }
}