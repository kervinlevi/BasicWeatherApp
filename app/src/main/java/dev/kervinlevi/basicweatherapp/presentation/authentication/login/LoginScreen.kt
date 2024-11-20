package dev.kervinlevi.basicweatherapp.presentation.authentication.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import dev.kervinlevi.basicweatherapp.presentation.navigation.AuthenticationNav
import dev.kervinlevi.basicweatherapp.presentation.navigation.NavGraph

/**
 * Created by kervinlevi on 20/11/24
 */
@Composable
fun LoginScreen(
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold { innerPadding ->
        Box(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(color = Color.Magenta)) {
            Column {
                Text(text = "Login screen")
                Button(onClick = { rootNavController.navigate(AuthenticationNav.SignUp.route) }) {
                    Text(text = "Not yet registered? Click here.")
                }
            }

        }
    }
}
