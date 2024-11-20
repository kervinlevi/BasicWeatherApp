package dev.kervinlevi.basicweatherapp.presentation.home.weatherreport

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportAction.OnPullToRefresh
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportAction.PermissionGranted
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportAction.ShowPermissionsRationale

/**
 * Created by kervinlevi on 19/11/24
 */
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WeatherReportScreen(
    state: WeatherReportState,
    onAction: (WeatherReportAction) -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.requestLocationPermissions) {
        RequestLocationPermission(onAction)
    }

    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = { onAction(OnPullToRefresh) }) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "City: ${state.location?.city}, ${state.location?.country}")
            Text(text = "Weather: ${state.weatherReport?.description}, ${state.weatherReport?.condition}")
            Text(text = "Temperature: ${state.weatherReport?.temperature} C")
            Text(text = "Sunrise: ${state.weatherReport?.sunriseTimestamp}")
            Text(text = "Sunset: ${state.weatherReport?.sunsetTimestamp}")
            Button(onClick = { navigateToLogin() }) {
                Text(text = "Log In")
            }

            state.error?.let { error ->
                when (error) {
                    WeatherReportError.NoInternet -> {
                        Text(text = "No Internet connection.")
                    }

                    is WeatherReportError.HttpError -> {
                        Text(text = "We encountered an error.")
                        Text(text = error.message)
                    }

                    WeatherReportError.LocationUnavailable -> {
                        Text(text = "Your location cannot be retrieved.")
                        Text(text = "Make sure you granted the permission.")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(onAction: (WeatherReportAction) -> Unit) {
    val permissionsState = rememberMultiplePermissionsState(
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

    LaunchedEffect(key1 = permissionsState) {
        if (!permissionsState.permissions.first().status.isGranted &&
            !permissionsState.permissions.last().status.isGranted &&
            permissionsState.shouldShowRationale
        ) {
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
}
