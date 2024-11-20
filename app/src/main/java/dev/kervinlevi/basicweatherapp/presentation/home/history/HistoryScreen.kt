package dev.kervinlevi.basicweatherapp.presentation.home.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import dev.kervinlevi.basicweatherapp.R
import dev.kervinlevi.basicweatherapp.domain.model.PastWeatherReport
import dev.kervinlevi.basicweatherapp.presentation.common.ui.Spacing
import dev.kervinlevi.basicweatherapp.presentation.common.ui.toSmallIconResource
import dev.kervinlevi.basicweatherapp.presentation.common.utils.DateTimeFormat.FullDate
import dev.kervinlevi.basicweatherapp.presentation.common.utils.DateTimeFormat.TimeOnly
import dev.kervinlevi.basicweatherapp.presentation.common.utils.formattedCelsius
import dev.kervinlevi.basicweatherapp.presentation.common.utils.formattedDateTime
import dev.kervinlevi.basicweatherapp.presentation.common.utils.formattedDescription
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by kervinlevi on 20/11/24
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    state: HistoryState, modifier: Modifier = Modifier
) {
    val simpleDateFormat = SimpleDateFormat(FullDate, Locale.getDefault())
    val simpleTimeFormat = SimpleDateFormat(TimeOnly, Locale.getDefault())
    val formatDate: (Long) -> String = { it.formattedDateTime(simpleDateFormat) }
    val formatTime: (Long) -> String = { it.formattedDateTime(simpleTimeFormat) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier.padding(horizontal = Spacing.normal)) {
            items(state.weatherReports) { pastReport ->
                PastReportCard(
                    data = pastReport, formatDate, formatTime, modifier = Modifier
                )
            }
        }

    }
}

@Composable
fun PastReportCard(
    data: PastWeatherReport,
    formatDate: (Long) -> String,
    formatTime: (Long) -> String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .padding(vertical = Spacing.small)
            .fillMaxWidth()
    ) {
        Card(
            modifier = modifier,
            elevation = CardDefaults.elevatedCardElevation(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.normal, horizontal = Spacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = data.weatherReport.condition.toSmallIconResource(),
                        ),
                        contentDescription = data.weatherReport.description,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(
                            id = R.string.celsius, data.weatherReport.temperature
                        ).formattedCelsius()
                    )
                }
                Spacer(modifier = Modifier.width(Spacing.large))
                Column {
                    Text(
                        text = "${data.location.city}, ${data.location.country}",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(
                            id = R.string.retrieved_on, formatDate(data.weatherReport.timestamp)
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(Spacing.normal))
                    Text(
                        text = data.weatherReport.description.formattedDescription(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(Spacing.normal))
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(0.75f))
                    Spacer(modifier = Modifier.height(Spacing.normal))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = formatTime(data.weatherReport.sunriseTimestamp),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = stringResource(R.string.sunrise),
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = formatTime(data.weatherReport.sunsetTimestamp),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = stringResource(R.string.sunset),
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }

                }
            }
        }
    }
}
