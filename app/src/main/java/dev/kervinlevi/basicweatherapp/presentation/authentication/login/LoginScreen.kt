package dev.kervinlevi.basicweatherapp.presentation.authentication.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import dev.kervinlevi.basicweatherapp.R
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginFieldError.EmailEmpty
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginFieldError.EmailWrongFormat
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginFieldError.PasswordEmpty
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginFieldError.PasswordWrongFormat
import dev.kervinlevi.basicweatherapp.presentation.common.ui.FieldErrorLabel
import dev.kervinlevi.basicweatherapp.presentation.common.ui.NavigationBackButton
import dev.kervinlevi.basicweatherapp.presentation.common.ui.PasswordVisibilityIcon
import dev.kervinlevi.basicweatherapp.presentation.common.ui.Spacing

/**
 * Created by kervinlevi on 20/11/24
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginState: LoginState,
    onAction: (LoginAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (loginState.hasLoggedIn) {
        navigateToHome()
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = navigateBack) { NavigationBackButton() }
        })
    }) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(PaddingValues(Spacing.large))
            ) {
                Spacer(modifier = Modifier.height(Spacing.xxlarge))
                OutlinedTextField(
                    value = loginState.email,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { onAction(LoginAction.UpdateEmail(it)) },
                    label = { Text(stringResource(id = R.string.email_address)) },
                    isError = loginState.emailError != null,
                    supportingText = {
                        when (loginState.emailError) {
                            is EmailEmpty -> stringResource(id = R.string.empty_email)
                            is EmailWrongFormat -> stringResource(id = R.string.invalid_email)
                            else -> null
                        }?.run {
                            FieldErrorLabel(errorText = this)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Spacing.small))
                var passwordVisible by rememberSaveable { mutableStateOf(false) }
                OutlinedTextField(value = loginState.password,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { onAction(LoginAction.UpdatePassword(it)) },
                    label = { Text(stringResource(id = R.string.password)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            PasswordVisibilityIcon(passwordVisible)
                        }
                    },
                    isError = loginState.passwordError != null,
                    supportingText = {
                        when (loginState.passwordError) {
                            is PasswordEmpty -> stringResource(id = R.string.empty_password)
                            is PasswordWrongFormat -> stringResource(id = R.string.invalid_password)
                            else -> null
                        }?.run {
                            FieldErrorLabel(errorText = this)
                        }
                    })

                Spacer(modifier = Modifier.height(Spacing.large))
                Button(
                    onClick = { onAction(LoginAction.LogIn) }, enabled = !loginState.isLoggingIn
                ) {
                    Text(
                        text = stringResource(id = R.string.log_in),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(Spacing.large))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.75f))
                Spacer(modifier = Modifier.height(Spacing.small))
                TextButton(onClick = navigateToSignUp, enabled = !loginState.isLoggingIn) {
                    Text(text = stringResource(id = R.string.not_registered))
                }
            }
        }
    }
}
