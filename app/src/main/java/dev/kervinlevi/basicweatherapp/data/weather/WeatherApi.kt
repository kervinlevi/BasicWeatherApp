package dev.kervinlevi.basicweatherapp.data.weather

import dev.kervinlevi.basicweatherapp.data.model.WeatherReportRemoteData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by kervinlevi on 20/11/24
 */
interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") key: String,
        @Query("units") units: String = "metric"
    ): WeatherReportRemoteData
}
