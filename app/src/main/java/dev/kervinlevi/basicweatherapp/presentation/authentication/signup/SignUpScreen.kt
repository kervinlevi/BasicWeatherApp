package dev.kervinlevi.basicweatherapp.presentation.authentication.signup

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import dev.kervinlevi.basicweatherapp.R
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpFieldError.EmailEmpty
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpFieldError.EmailWrongFormat
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpFieldError.NameEmpty
import dev.kervinlevi.basicweatherapp.presentation.common.ui.FieldErrorLabel
import dev.kervinlevi.basicweatherapp.presentation.common.ui.NavigationBackButton
import dev.kervinlevi.basicweatherapp.presentation.common.ui.PasswordVisibilityIcon
import dev.kervinlevi.basicweatherapp.presentation.common.ui.Spacing

/**
 * Created by kervinlevi on 20/11/24
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    signUpState: SignUpState,
    onAction: (SignUpAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (signUpState.hasSignedUp) {
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
                Text(
                    text = stringResource(id = R.string.registration),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(Spacing.small))
                OutlinedTextField(
                    value = signUpState.name,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                    onValueChange = { onAction(SignUpAction.UpdateName(it)) },
                    label = { Text(stringResource(R.string.name)) },
                    isError = signUpState.nameError != null,
                    supportingText = {
                        if (signUpState.nameError == NameEmpty) {
                            FieldErrorLabel(errorText = stringResource(id = R.string.empty_name))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(Spacing.small))
                OutlinedTextField(
                    value = signUpState.email,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { onAction(SignUpAction.UpdateEmail(it)) },
                    label = { Text(stringResource(id = R.string.email_address)) },
                    isError = signUpState.emailError != null,
                    supportingText = {
                        when (signUpState.emailError) {
                            is EmailEmpty -> stringResource(id = R.string.empty_email)
                            is EmailWrongFormat -> stringResource(id = R.string.invalid_email)
                            else -> null
                        }?.run {
                            FieldErrorLabel(errorText = this)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(Spacing.small))
                var passwordVisible by rememberSaveable { mutableStateOf(false) }
                OutlinedTextField(value = signUpState.password,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { onAction(SignUpAction.UpdatePassword(it)) },
                    label = { Text(stringResource(id = R.string.password)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            PasswordVisibilityIcon(passwordVisible)
                        }
                    },
                    isError = signUpState.passwordError != null,
                    supportingText = {
                        when (signUpState.passwordError) {
                            is SignUpFieldError.PasswordEmpty -> stringResource(id = R.string.empty_password)
                            is SignUpFieldError.PasswordWrongFormat -> stringResource(id = R.string.invalid_password)
                            else -> null
                        }?.run {
                            FieldErrorLabel(errorText = this)
                        }
                    })

                Spacer(modifier = Modifier.height(Spacing.large))
                Button(
                    onClick = { onAction(SignUpAction.SignUp) }, enabled = !signUpState.isSigningUp
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
            }
        }
    }
}
