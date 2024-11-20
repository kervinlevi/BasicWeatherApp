package dev.kervinlevi.basicweatherapp.presentation.home.weatherreport

import dev.kervinlevi.basicweatherapp.domain.model.Location
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport

/**
 * Created by kervinlevi on 19/11/24
 */
data class WeatherReportState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean? = null,
    val requestLocationPermissions: Boolean = false,
    val showLocationPermissionRationale: Boolean = false,
    val location: Location? = null,
    val weatherReport: WeatherReport? = null,
    val error: WeatherReportError? = null
)

sealed interface WeatherReportAction {
    object ShowPermissionsRationale: WeatherReportAction
    object PermissionGranted: WeatherReportAction
    object OnPullToRefresh: WeatherReportAction

    object LogOut: WeatherReportAction
}

sealed interface WeatherReportError {
    object NoInternet: WeatherReportError
    data class HttpError(val message: String): WeatherReportError
    object LocationUnavailable: WeatherReportError
}
