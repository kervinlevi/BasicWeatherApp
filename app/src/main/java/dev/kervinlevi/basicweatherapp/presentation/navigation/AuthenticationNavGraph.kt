package dev.kervinlevi.basicweatherapp.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginScreen
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginViewModel
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpScreen
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpViewModel

/**
 * Created by kervinlevi on 20/11/24
 */

fun NavGraphBuilder.authenticationNavGraph(rootNavController: NavHostController) {
    navigation(
        route = NavGraph.AUTHENTICATION, startDestination = AuthenticationNav.Login.route
    ) {
        val navigateToHome: () -> Unit = {
            rootNavController.popBackStack(NavGraph.ROOT, inclusive = false)
            rootNavController.navigate(NavGraph.HOME)
        }
        val navigateBack: () -> Unit = {
            rootNavController.popBackStack()
        }

        composable(route = AuthenticationNav.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel.loginState.value,
                viewModel::onAction,
                navigateBack,
                navigateToHome,
                navigateToSignUp = { rootNavController.navigate(AuthenticationNav.SignUp.route) }
            )
        }
        composable(route = AuthenticationNav.SignUp.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                viewModel.signUpState.value,
                viewModel::onAction,
                navigateBack,
                navigateToHome
            )
        }
    }
}

sealed class AuthenticationNav(val route: String) {
    object Login : AuthenticationNav("login")
    object SignUp : AuthenticationNav("signup")
}
