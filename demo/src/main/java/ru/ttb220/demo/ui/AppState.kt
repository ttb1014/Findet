package ru.ttb220.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ttb220.demo.navigation.Destination

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember {
    AppState(navController)
}

@Stable
class AppState(
    val navHostController: NavHostController,
) {
    val currentRoute: String?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination?.route

    val currentDestination: Destination?
        @Composable get() {
            return Destination.entries.firstOrNull { it.name == currentRoute }
        }

    fun navigateTo(destination: String) {
        navHostController.navigate(destination)
    }
}