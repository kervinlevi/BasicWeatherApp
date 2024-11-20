package dev.kervinlevi.basicweatherapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kervinlevi.basicweatherapp.presentation.home.history.HistoryScreen
import dev.kervinlevi.basicweatherapp.presentation.home.history.HistoryViewModel
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportScreen
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportViewModel

/**
 * Created by kervinlevi on 20/11/24
 */
@Composable
fun HomeNavGraph(
    rootNavController: NavHostController, navController: NavHostController, modifier: Modifier
) {
    NavHost(
        navController = navController,
        route = NavGraph.HOME,
        startDestination = BottomBarNav.WeatherReport.route,
        modifier = modifier
    ) {
        composable(route = BottomBarNav.WeatherReport.route) {
            val viewModel: WeatherReportViewModel = hiltViewModel()
            WeatherReportScreen(state = viewModel.weatherReportState.value,
                onAction = viewModel::onAction,
                navigateToLogin = { rootNavController.navigate(NavGraph.AUTHENTICATION) })
        }

        composable(route = BottomBarNav.History.route) {
            val viewModel: HistoryViewModel = hiltViewModel()
            HistoryScreen(state = viewModel.historyState.value)
        }
    }
}

sealed class BottomBarNav(val route: String, val title: String, val icon: ImageVector) {
    object WeatherReport : BottomBarNav("weatherreport", "Weather", Icons.Default.Place)
    object History : BottomBarNav("history", "History", Icons.Default.List)
}
