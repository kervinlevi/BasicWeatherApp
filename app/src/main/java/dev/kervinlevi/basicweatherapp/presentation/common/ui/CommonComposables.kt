package dev.kervinlevi.basicweatherapp.presentation.common.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.kervinlevi.basicweatherapp.R

/**
 * Created by kervinlevi on 21/11/24
 */

@Composable
fun FieldErrorLabel(errorText: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = errorText,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
fun NavigationBackButton() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = stringResource(id = R.string.back)
    )
}

@Composable
fun PasswordVisibilityIcon(passwordVisible: Boolean) {
    val resId = if (passwordVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off
    Icon(
        painter = painterResource(id = resId),
        contentDescription = stringResource(id = R.string.visibility_toggle)
    )
}
