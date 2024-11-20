package dev.kervinlevi.basicweatherapp.domain.model

/**
 * Created by kervinlevi on 20/11/24
 */
data class PastWeatherReport(
    val location: Location,
    val weatherReport: WeatherReport
)
