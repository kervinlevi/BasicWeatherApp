package dev.kervinlevi.basicweatherapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kervinlevi on 20/11/24
 */
@JsonClass(generateAdapter = true)
data class SystemWeatherRemoteData(
    @Json(name = "country") val country: String,

    @Json(name = "sunrise") val sunrise: Long,

    @Json(name = "sunset") val sunset: Long
)
