package dev.kervinlevi.basicweatherapp.data.mapper

import androidx.room.ColumnInfo
import dev.kervinlevi.basicweatherapp.data.db.PastWeatherEntity
import dev.kervinlevi.basicweatherapp.domain.model.Location
import dev.kervinlevi.basicweatherapp.domain.model.PastWeatherReport
import dev.kervinlevi.basicweatherapp.domain.model.WeatherCondition
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport

/**
 * Created by kervinlevi on 20/11/24
 */
fun Pair<Location, WeatherReport>.toPastWeatherEntity(): PastWeatherEntity {
    val location = this.first
    val weatherReport = this.second
    return PastWeatherEntity(
        latitude = location.latitude,
        longitude = location.longitude,
        city = location.city,
        country = location.country,
        temperature = weatherReport.temperature,
        condition = weatherReport.condition,
        timestamp = weatherReport.timestamp,
        sunriseTimestamp = weatherReport.sunriseTimestamp,
        sunsetTimestamp = weatherReport.sunsetTimestamp,
        humidity = weatherReport.humidity,
        description = weatherReport.description
    )
}

fun PastWeatherEntity.toDomainModel(): PastWeatherReport {
    val location = Location(latitude, longitude, city, country)
    val weatherReport = WeatherReport(
        temperature,
        condition,
        timestamp,
        sunriseTimestamp,
        sunsetTimestamp,
        humidity,
        description
    )
    return PastWeatherReport(location, weatherReport)
}
