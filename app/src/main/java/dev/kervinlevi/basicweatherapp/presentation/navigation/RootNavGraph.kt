package dev.kervinlevi.basicweatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kervinlevi.basicweatherapp.presentation.home.HomeScreen

/**
 * Created by kervinlevi on 20/11/24
 */
@Composable
fun RootNavigationGraph(rootNavController: NavHostController) {
    NavHost(
        navController = rootNavController, route = NavGraph.ROOT, startDestination = NavGraph.HOME
    ) {
        authenticationNavGraph(rootNavController)
        composable(NavGraph.HOME) {
            HomeScreen(rootNavController)
        }
    }
}

object NavGraph {
    const val ROOT = "root_nav_graph"
    const val AUTHENTICATION = "auth_nav_graph"
    const val HOME = "home_nav_graph"
}
