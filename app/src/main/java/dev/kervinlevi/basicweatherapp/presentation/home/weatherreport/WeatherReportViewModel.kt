package dev.kervinlevi.basicweatherapp.presentation.home.weatherreport

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kervinlevi.basicweatherapp.domain.location.LocationProvider
import dev.kervinlevi.basicweatherapp.domain.model.ApiResponse
import dev.kervinlevi.basicweatherapp.domain.weather.WeatherRepository
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportError.LocationUnavailable
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
        val locationPermissionGranted = locationProvider.isLocationPermissionGranted()
        weatherReportState.value = weatherReportState.value.copy(
            requestLocationPermissions = !locationPermissionGranted,
            error = if (!locationPermissionGranted) LocationUnavailable else null
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

            WeatherReportAction.OnPullToRefresh -> {
                requestLocationAndWeather()
            }
        }
    }

    private fun requestLocationAndWeather() = viewModelScope.launch {
        if (weatherReportState.value.isLoading) return@launch
        weatherReportState.value = weatherReportState.value.copy(isLoading = true)

        val location = locationProvider.getLocation()
        if (location != null) {
            when (val apiResponse = weatherRepository.getCurrentWeather(location)) {
                is ApiResponse.Success -> {
                    weatherReportState.value = weatherReportState.value.copy(
                        isLoading = false,
                        location = location,
                        weatherReport = apiResponse.data,
                        error = null
                    )
                }

                is ApiResponse.Error -> {
                    val exception = apiResponse.exception
                    val error = if (exception is HttpException) {
                        WeatherReportError.HttpError("${exception.code()}: ${exception.message()}")
                    } else {
                        WeatherReportError.NoInternet
                    }
                    weatherReportState.value = weatherReportState.value.copy(
                        isLoading = false,
                        location = location,
                        weatherReport = null,
                        error = error
                    )
                }
            }
        } else {
            weatherReportState.value = weatherReportState.value.copy(
                isLoading = false,
                location = null,
                weatherReport = null,
                error = LocationUnavailable
            )
        }
    }
}
