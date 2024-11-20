package dev.kervinlevi.basicweatherapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginScreen
import dev.kervinlevi.basicweatherapp.presentation.authentication.register.SignUpScreen

/**
 * Created by kervinlevi on 20/11/24
 */

fun NavGraphBuilder.authenticationNavGraph(rootNavController: NavHostController) {
    navigation(
        route = NavGraph.AUTHENTICATION, startDestination = AuthenticationNav.Login.route
    ) {
        composable(route = AuthenticationNav.Login.route) {
            LoginScreen(rootNavController)
        }
        composable(route = AuthenticationNav.SignUp.route) {
            SignUpScreen(rootNavController)
        }
    }
}

sealed class AuthenticationNav(val route: String) {
    object Login : AuthenticationNav("login")
    object SignUp : AuthenticationNav("signup")
}
