package dev.kervinlevi.basicweatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.kervinlevi.basicweatherapp.presentation.navigation.RootNavigationGraph
import dev.kervinlevi.basicweatherapp.presentation.common.ui.BasicWeatherAppTheme


/**
 * Created by kervinlevi on 19/11/24
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicWeatherAppTheme {
                RootNavigationGraph(rootNavController = rememberNavController())
            }
        }
    }
}
