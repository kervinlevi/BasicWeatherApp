package dev.kervinlevi.basicweatherapp.presentation.home.weatherreport

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kervinlevi.basicweatherapp.domain.location.LocationProvider
import dev.kervinlevi.basicweatherapp.domain.weather.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by kervinlevi on 19/11/24
 */
@HiltViewModel
class WeatherReportViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    var weatherReportState = mutableStateOf(WeatherReportState())
        private set

    init {
        weatherReportState.value = weatherReportState.value.copy(
            requestLocationPermissions = !locationProvider.isLocationPermissionGranted(),
        )
        if (locationProvider.isLocationPermissionGranted()) {
            requestLocationAndWeather()
        }
    }

    fun onAction(action: WeatherReportAction) {
        when (action) {
            is WeatherReportAction.PermissionGranted -> {
                weatherReportState.value = weatherReportState.value.copy(
                    requestLocationPermissions = false,
                    showLocationPermissionRationale = false
                )
                requestLocationAndWeather()
            }
            is WeatherReportAction.ShowPermissionsRationale -> {
                weatherReportState.value = weatherReportState.value.copy(
                    requestLocationPermissions = false,
                    showLocationPermissionRationale = true
                )
            }
        }
    }

    fun requestLocationAndWeather() = viewModelScope.launch {
        weatherReportState.value = weatherReportState.value.copy(isLoading = true)

        locationProvider.getLocation()?.let { location ->
            val weatherReport = weatherRepository.getCurrentWeather(location)
            weatherReportState.value = weatherReportState.value.copy(
                isLoading = false,
                location = location,
                weatherReport = weatherReport
            )
        }
    }
}
