package dev.kervinlevi.basicweatherapp.presentation.home.weatherreport

import android.Manifest
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dev.kervinlevi.basicweatherapp.R
import dev.kervinlevi.basicweatherapp.domain.model.WeatherCondition
import dev.kervinlevi.basicweatherapp.presentation.common.ui.Blue50
import dev.kervinlevi.basicweatherapp.presentation.common.ui.Purple40
import dev.kervinlevi.basicweatherapp.presentation.common.ui.Spacing
import dev.kervinlevi.basicweatherapp.presentation.common.ui.toLottieAsset
import dev.kervinlevi.basicweatherapp.presentation.common.utils.DateTimeFormat
import dev.kervinlevi.basicweatherapp.presentation.common.utils.formattedCelsius
import dev.kervinlevi.basicweatherapp.presentation.common.utils.formattedDateTime
import dev.kervinlevi.basicweatherapp.presentation.common.utils.formattedDescription
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportAction.LogOut
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportAction.OnPullToRefresh
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportAction.PermissionGranted
import dev.kervinlevi.basicweatherapp.presentation.home.weatherreport.WeatherReportAction.ShowPermissionsRationale
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by kervinlevi on 19/11/24
 */
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WeatherReportScreen(
    state: WeatherReportState,
    onAction: (WeatherReportAction) -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.requestLocationPermissions) {
        RequestLocationPermission(onAction)
    }
    val simpleDateFormat = SimpleDateFormat(DateTimeFormat.FullDate, Locale.getDefault())
    val simpleTimeFormat = SimpleDateFormat(DateTimeFormat.TimeOnly, Locale.getDefault())
    val formatDate: (Long) -> String = { it.formattedDateTime(simpleDateFormat) }
    val formatTime: (Long) -> String = { it.formattedDateTime(simpleTimeFormat) }

    PullToRefreshBox(isRefreshing = state.isLoading, onRefresh = { onAction(OnPullToRefresh) }) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surfaceBright),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (state.isLoggedIn == true) {
                    TextButton(
                        onClick = { onAction(LogOut) }, modifier = Modifier.padding(Spacing.medium)
                    ) {
                        Text(text = stringResource(id = R.string.log_out))
                    }
                } else if (state.isLoggedIn == false) {
                    TextButton(
                        onClick = { navigateToLogin() },
                        modifier = Modifier.padding(Spacing.medium)
                    ) {
                        Text(text = stringResource(id = R.string.log_in))
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Spacing.xlarge)
                    .background(MaterialTheme.colorScheme.background)
            )
            Box(
                modifier = modifier.background(
                    Brush.verticalGradient(
                        listOf(MaterialTheme.colorScheme.background, Blue50)
                    )
                )
            ) {
                Canvas(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = Spacing.medium)
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .clipToBounds()
                ) {
                    drawArc(
                        color = Purple40,
                        -180f,
                        180f,
                        useCenter = false,
                        size = Size(size.width, size.height * 2)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .aspectRatio(0.9f)
                        .align(Alignment.Center)
                ) {
                    LottieWeatherAnimation(
                        state.weatherReport?.condition,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }

                state.weatherReport?.temperature?.let { temperature ->
                    Text(
                        modifier = Modifier.align(alignment = Alignment.BottomCenter),
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        text = stringResource(id = R.string.celsius, temperature).formattedCelsius()
                    )
                }
            }
            HorizontalDivider()

            state.weatherReport?.let { weatherReport ->
                weatherReport.description?.formattedDescription()?.let { description ->
                    Spacer(modifier = Modifier.height(Spacing.normal))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        fontStyle = FontStyle.Italic
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.normal))
                Text(
                    text = "${state.location?.city}, ${state.location?.country}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(
                        id = R.string.retrieved_on, formatDate(weatherReport.timestamp)
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(Spacing.xxlarge))
                Row(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = formatTime(weatherReport.sunriseTimestamp),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Text(
                            text = stringResource(R.string.sunrise),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = formatTime(weatherReport.sunsetTimestamp),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Text(
                            text = stringResource(R.string.sunset),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }

            state.error?.let { error ->
                Spacer(modifier = Modifier.height(Spacing.normal))
                when (error) {
                    WeatherReportError.NoInternet -> {
                        Text(
                            text = stringResource(R.string.no_internet_connection),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    is WeatherReportError.HttpError -> {
                        Text(
                            text = stringResource(R.string.http_error),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = error.message,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = modifier.padding(top = Spacing.small)
                        )
                    }

                    WeatherReportError.LocationUnavailable -> {
                        Text(
                            text = stringResource(R.string.location_error_title),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = stringResource(R.string.location_error_description),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(
                                horizontal = Spacing.normal,
                                vertical = Spacing.small
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        val context = LocalContext.current
                        Button(onClick = {
                            val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                                addFlags(FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(intent)
                        }) {
                            Text(text = stringResource(R.string.location_error_button))
                        }
                        TextButton(onClick = {
                            val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                            context.startActivity(intent)
                        }) {
                            Text(text = stringResource(R.string.location_error_settings_button))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LottieWeatherAnimation(weatherCondition: WeatherCondition?, modifier: Modifier) {
    val assetName = weatherCondition.toLottieAsset()
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset(assetName)
    )
    LottieAnimation(
        composition = composition, iterations = LottieConstants.IterateForever, modifier = modifier
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(onAction: (WeatherReportAction) -> Unit) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = RequestMultiplePermissions()) { granted ->
            if (granted.containsValue(true)) {
                onAction(PermissionGranted)
            } else {
                onAction(ShowPermissionsRationale)
            }
        }

    LaunchedEffect(key1 = permissionsState) {
        if (!permissionsState.permissions.first().status.isGranted && !permissionsState.permissions.last().status.isGranted && permissionsState.shouldShowRationale) {
            onAction(ShowPermissionsRationale)
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}
