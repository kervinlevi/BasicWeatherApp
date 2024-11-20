package dev.kervinlevi.basicweatherapp.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.kervinlevi.basicweatherapp.presentation.navigation.BottomBarNav
import dev.kervinlevi.basicweatherapp.presentation.navigation.HomeNavGraph

/**
 * Created by kervinlevi on 20/11/24
 */
@Composable
fun HomeScreen(
    rootNavController: NavHostController,
    homeNavController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BottomBar(navController = homeNavController) }
    ) { innerPadding ->
        HomeNavGraph(
            rootNavController = rootNavController,
            navController = homeNavController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val tabs = listOf(
        BottomBarNav.WeatherReport,
        BottomBarNav.History
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selected = navBackStackEntry?.destination

    NavigationBar {
        tabs.forEach {
            NavigationBarItem(
                selected = it.route == selected?.route,
                label = {
                    Text(text = it.title)
                },
                icon = {
                    Icon(
                        it.icon,
                        contentDescription = it.title
                    )
                },
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }


}