package dev.kervinlevi.basicweatherapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kervinlevi on 20/11/24
 */
@JsonClass(generateAdapter = true)
data class WeatherRemoteData(
    @Json(name = "description") val description: String,

    @Json(name = "icon") val icon: String,

    @Json(name = "id") val id: Long,

    @Json(name = "main") val main: String
)
