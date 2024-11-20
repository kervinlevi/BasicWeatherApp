package dev.kervinlevi.basicweatherapp.data.mapper

import dev.kervinlevi.basicweatherapp.data.model.WeatherReportRemoteData
import dev.kervinlevi.basicweatherapp.domain.model.WeatherCondition
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport

/**
 * Created by kervinlevi on 20/11/24
 */

fun WeatherReportRemoteData.toDomainModel(): WeatherReport {
    return WeatherReport(
        temperature = mainWeather.temperature,
        humidity = mainWeather.humidity,
        timestamp = dateTime,
        sunriseTimestamp = system.sunrise,
        sunsetTimestamp = system.sunset,
        condition = getWeatherCondition(weather.firstOrNull()?.icon),
        description = weather.firstOrNull()?.description
    )
}

private fun getWeatherCondition(icon: String?): WeatherCondition {
    if (icon.isNullOrEmpty()) return WeatherCondition.UNKNOWN
    val number = icon.dropLast(1).toIntOrNull() ?: return WeatherCondition.UNKNOWN
    val isDay = icon.endsWith("d", ignoreCase = true)

    return when {
        number in 1..2 && isDay -> WeatherCondition.DAY_CLEAR_SKY
        number in 1..2 && !isDay -> WeatherCondition.NIGHT_CLEAR_SKY
        number in 3 .. 4 && isDay -> WeatherCondition.DAY_CLOUDY
        number in 3 .. 4 && !isDay -> WeatherCondition.NIGHT_CLOUDY
        number in 9 .. 11 && isDay -> WeatherCondition.DAY_RAIN
        number in 9 .. 11 && !isDay -> WeatherCondition.NIGHT_RAIN
        number == 13 && isDay -> WeatherCondition.DAY_SNOW
        number == 13 && !isDay -> WeatherCondition.NIGHT_SNOW
        else -> WeatherCondition.UNKNOWN
    }
}
