package dev.kervinlevi.basicweatherapp.data.model

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

/**
 * Created by kervinlevi on 20/11/24
 */
@JsonClass(generateAdapter = true)
data class MainWeatherRemoteData(
    @Json(name = "humidity") val humidity: Int,

    @Json(name = "temp") val temperature: Double
)
