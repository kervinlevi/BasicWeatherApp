package dev.kervinlevi.basicweatherapp.presentation.home.history

import dev.kervinlevi.basicweatherapp.domain.model.PastWeatherReport

/**
 * Created by kervinlevi on 20/11/24
 */
data class HistoryState(
    val isLoading: Boolean = false,
    val weatherReports: List<PastWeatherReport> = emptyList()
)
