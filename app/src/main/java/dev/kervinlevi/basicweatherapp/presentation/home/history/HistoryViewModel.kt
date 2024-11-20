package dev.kervinlevi.basicweatherapp.presentation.home.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kervinlevi.basicweatherapp.domain.weather.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by kervinlevi on 20/11/24
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    var historyState = mutableStateOf(HistoryState())
        private set

    init {
        getPastWeatherReports()
    }

    fun getPastWeatherReports() = viewModelScope.launch {
        historyState.value = historyState.value.copy(isLoading = true)
        val reports = weatherRepository.getPastReports()
        historyState.value = historyState.value.copy(isLoading = false, weatherReports = reports)
    }
}
