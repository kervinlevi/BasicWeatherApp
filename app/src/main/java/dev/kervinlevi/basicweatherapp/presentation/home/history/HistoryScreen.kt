package dev.kervinlevi.basicweatherapp.presentation.home.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Created by kervinlevi on 20/11/24
 */
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = Color.Blue)) {
        Text(text = "History screen")
    }
}
