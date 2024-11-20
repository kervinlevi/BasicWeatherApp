package dev.kervinlevi.basicweatherapp.testdata

import dev.kervinlevi.basicweatherapp.domain.model.PastWeatherReport
import dev.kervinlevi.basicweatherapp.domain.model.WeatherCondition
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport

/**
 * Created by kervinlevi on 20/11/24
 */
val testWeatherReport1 = WeatherReport(
    temperature = 28.34,
    condition = WeatherCondition.DAY_CLEAR_SKY,
    timestamp = 1732110763L,
    humidity = 76,
    description = "clear sky",
    sunriseTimestamp = 1732053553L,
    sunsetTimestamp = 1732094636L
)

val testWeatherReport2 = WeatherReport(
    temperature = 26.03,
    condition = WeatherCondition.NIGHT_CLOUDY,
    timestamp = 1732111394L,
    humidity = 80,
    description = "scattered clouds",
    sunriseTimestamp = 1732053562L,
    sunsetTimestamp = 1732094635L
)

val testPastReport1 = PastWeatherReport(
    location = testLocation1,
    weatherReport = testWeatherReport1
)

val testPastReport2 = PastWeatherReport(
    location = testLocation2,
    weatherReport = testWeatherReport2
)
