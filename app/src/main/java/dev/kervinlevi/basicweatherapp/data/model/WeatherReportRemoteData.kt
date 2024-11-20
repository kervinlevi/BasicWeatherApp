package dev.kervinlevi.basicweatherapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kervinlevi on 20/11/24
 */
@JsonClass(generateAdapter = true)
data class WeatherReportRemoteData(
    @Json(name = "dt") val dateTime: Long,

    @Json(name = "main") val mainWeather: MainWeatherRemoteData,

    @Json(name = "sys") val system: SystemWeatherRemoteData,

    @Json(name = "weather") val weather: List<WeatherRemoteData>,

    @Json(name = "name") val cityName: String,

    @Json(name = "timezone") val timezone: Long
)
