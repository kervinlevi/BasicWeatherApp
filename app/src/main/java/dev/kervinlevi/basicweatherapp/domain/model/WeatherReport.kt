package dev.kervinlevi.basicweatherapp.domain.model

/**
 * Created by kervinlevi on 20/11/24
 */

data class WeatherReport(
    val temperature: Double,
    val condition: WeatherCondition,
    val timestamp: Long,
    val sunriseTimestamp: Long,
    val sunsetTimestamp: Long,
    val humidity: Int,
    val description: String? = null
)

enum class WeatherCondition {
    DAY_CLEAR_SKY, DAY_CLOUDY, DAY_RAIN, DAY_SNOW,
    NIGHT_CLEAR_SKY, NIGHT_CLOUDY, NIGHT_RAIN, NIGHT_SNOW, UNKNOWN
}
