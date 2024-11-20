package dev.kervinlevi.basicweatherapp.presentation.home.weatherreport

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportAction.*

/**
 * Created by kervinlevi on 19/11/24
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherReportScreen(
    state: WeatherReportState,
    onAction: (WeatherReportAction) -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = RequestMultiplePermissions()) { granted ->
            if (granted.containsValue(true)) {
                onAction(PermissionGranted)
            } else {
                onAction(ShowPermissionsRationale)
            }
        }

    LaunchedEffect(key1 = locationPermissionState) {
        if (!locationPermissionState.permissions.first().status.isGranted && !locationPermissionState.permissions.last().status.isGranted && locationPermissionState.shouldShowRationale) {
            onAction(ShowPermissionsRationale)
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(text = "City: ${state.location?.city}, ${state.location?.country}")
        Text(text = "Weather: ${state.weatherReport?.description}, ${state.weatherReport?.condition}")
        Text(text = "Temperature: ${state.weatherReport?.temperature} C")
        Text(text = "Sunrise: ${state.weatherReport?.sunriseTimestamp}")
        Text(text = "Sunset: ${state.weatherReport?.sunsetTimestamp}")
        Button(onClick = { navigateToLogin() }) {
            Text(text = "Log In")
        }
    }
}
