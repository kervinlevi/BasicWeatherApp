package dev.kervinlevi.basicweatherapp.data.weather

import dev.kervinlevi.basicweatherapp.data.mapper.toDomainModel
import dev.kervinlevi.basicweatherapp.data.model.WeatherReportRemoteData
import dev.kervinlevi.basicweatherapp.domain.model.Location
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport
import dev.kervinlevi.basicweatherapp.domain.weather.WeatherRepository

/**
 * Created by kervinlevi on 20/11/24
 */
class WeatherRepositoryImpl(private val weatherApi: WeatherApi) : WeatherRepository {

    override suspend fun getCurrentWeather(location: Location): WeatherReport {
        val remoteData = weatherApi.getWeather(
            latitude = location.latitude.toString(),
            longitude = location.longitude.toString(),
            key = ""
        )
        return remoteData.toDomainModel()
    }
}
