package dev.kervinlevi.basicweatherapp.data.weather

/**
 * Created by kervinlevi on 20/11/24
 */
object WeatherApiAppId: WeatherApiAppIdProvider {
    init {
        System.loadLibrary("keyholder")
    }
    external override fun appId(): String
}

interface WeatherApiAppIdProvider {
    fun appId(): String
}
