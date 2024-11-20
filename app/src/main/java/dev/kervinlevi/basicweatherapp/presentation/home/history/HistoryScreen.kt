package dev.kervinlevi.basicweatherapp.presentation.home.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kervinlevi.basicweatherapp.domain.model.PastWeatherReport

/**
 * Created by kervinlevi on 20/11/24
 */
@Composable
fun HistoryScreen(
    state: HistoryState, modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Past weather reports")

        LazyColumn {
            items(state.weatherReports) { pastReport ->
                PastReportCard(data = pastReport, modifier = Modifier)
            }
        }

    }
}

@Composable
fun PastReportCard(data: PastWeatherReport, modifier: Modifier) {
    Card(modifier = modifier) {
        Column {
            Text(text = "Timestamp: ${data.weatherReport.timestamp}")
            Text(text = "Location: ${data.location.city}, ${data.location.country}")
            Text(text = "Description: ${data.weatherReport.description}")

        }
    }
}
