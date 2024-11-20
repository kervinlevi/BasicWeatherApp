package dev.kervinlevi.basicweatherapp.domain.weather

import dev.kervinlevi.basicweatherapp.domain.model.Location
import dev.kervinlevi.basicweatherapp.domain.model.PastWeatherReport
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport

/**
 * Created by kervinlevi on 20/11/24
 */
interface WeatherRepository {

    suspend fun getCurrentWeather(location: Location): WeatherReport
    suspend fun getPastReports(): List<PastWeatherReport>
}
