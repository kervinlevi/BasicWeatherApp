package dev.kervinlevi.basicweatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportScreen
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportViewModel
import dev.kervinlevi.basicweatherapp.ui.theme.BasicWeatherAppTheme


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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewmodel: WeatherReportViewModel = hiltViewModel()
                    WeatherReportScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = viewmodel.weatherReportState.value,
                        onAction = viewmodel::onAction
                    )
                }
            }
        }
    }
}
