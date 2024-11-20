package dev.kervinlevi.basicweatherapp.presentation.home.weatherreport

import dev.kervinlevi.basicweatherapp.domain.model.Location
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport

/**
 * Created by kervinlevi on 19/11/24
 */
data class WeatherReportState(
    val isLoading: Boolean = false,
    val requestLocationPermissions: Boolean = false,
    val showLocationPermissionRationale: Boolean = false,
    val location: Location? = null,
    val weatherReport: WeatherReport? = null
)

sealed class WeatherReportAction {
    object ShowPermissionsRationale: WeatherReportAction()
    object PermissionGranted: WeatherReportAction()

}
