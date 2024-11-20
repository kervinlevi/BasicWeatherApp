package dev.kervinlevi.basicweatherapp.data.weather

import dev.kervinlevi.basicweatherapp.data.db.PastWeatherDao
import dev.kervinlevi.basicweatherapp.data.mapper.toDomainModel
import dev.kervinlevi.basicweatherapp.data.mapper.toPastWeatherEntity
import dev.kervinlevi.basicweatherapp.data.model.WeatherReportRemoteData
import dev.kervinlevi.basicweatherapp.domain.model.Location
import dev.kervinlevi.basicweatherapp.domain.model.PastWeatherReport
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport
import dev.kervinlevi.basicweatherapp.domain.weather.WeatherRepository

/**
 * Created by kervinlevi on 20/11/24
 */
class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val pastWeatherDao: PastWeatherDao,
    private val weatherApiAppIdProvider: WeatherApiAppIdProvider
) : WeatherRepository {

    override suspend fun getCurrentWeather(location: Location): WeatherReport {
        val remoteData = weatherApi.getWeather(
            latitude = location.latitude.toString(),
            longitude = location.longitude.toString(),
            key = weatherApiAppIdProvider.appId()
        )
        val weatherReport = remoteData.toDomainModel()
        val pastWeather = (location to weatherReport).toPastWeatherEntity()
        pastWeatherDao.insert(pastWeather)
        return weatherReport
    }

    override suspend fun getPastReports(): List<PastWeatherReport> {
        return pastWeatherDao.getMostRecent().map { it.toDomainModel() }
    }
}
