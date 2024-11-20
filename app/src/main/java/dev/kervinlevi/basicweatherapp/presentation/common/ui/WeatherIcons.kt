package dev.kervinlevi.basicweatherapp.presentation.common.ui

import dev.kervinlevi.basicweatherapp.R
import dev.kervinlevi.basicweatherapp.domain.model.WeatherCondition

/**
 * Created by kervinlevi on 21/11/24
 */
fun WeatherCondition.toSmallIconResource(): Int {
    return when (this) {
        WeatherCondition.DAY_CLEAR_SKY -> R.drawable.ic_day_clear_sky
        WeatherCondition.DAY_CLOUDY -> R.drawable.ic_day_cloudy
        WeatherCondition.DAY_RAIN -> R.drawable.ic_day_rain
        WeatherCondition.DAY_SNOW -> R.drawable.ic_day_snow
        WeatherCondition.NIGHT_CLEAR_SKY -> R.drawable.ic_night_clear_sky
        WeatherCondition.NIGHT_CLOUDY -> R.drawable.ic_night_cloudy
        WeatherCondition.NIGHT_RAIN -> R.drawable.ic_night_rain
        WeatherCondition.NIGHT_SNOW -> R.drawable.ic_night_snow
        else -> R.drawable.ic_unknown
    }
}

fun WeatherCondition?.toLottieAsset(): String {
    return when (this) {
        WeatherCondition.DAY_CLEAR_SKY -> "animations/day-clear-sky.json"
        WeatherCondition.DAY_CLOUDY -> "animations/day-cloudy.json"
        WeatherCondition.DAY_RAIN -> "animations/day-rain.json"
        WeatherCondition.DAY_SNOW -> "animations/day-snow.json"
        WeatherCondition.NIGHT_CLEAR_SKY -> "animations/night-clear-sky.json"
        WeatherCondition.NIGHT_CLOUDY -> "animations/night-cloudy.json"
        WeatherCondition.NIGHT_RAIN -> "animations/night-rain.json"
        WeatherCondition.NIGHT_SNOW -> "animations/night-snow.json"
        else -> "animations/unknown.json"
    }
}
