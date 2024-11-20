package dev.kervinlevi.basicweatherapp.presentation.authentication.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import dev.kervinlevi.basicweatherapp.R

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
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back"
                )
            }
        })
    }) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                Text(text = "Sign Up")
                OutlinedTextField(
                    value = signUpState.email,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { onAction(SignUpAction.UpdateEmail(it)) },
                    label = { Text("Email Address") },
                    modifier = Modifier,
                )

                OutlinedTextField(
                    value = signUpState.name,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                    onValueChange = { onAction(SignUpAction.UpdateName(it)) },
                    label = { Text("Name") },
                    modifier = Modifier,
                )

                var passwordVisible by rememberSaveable { mutableStateOf(false) }
                OutlinedTextField(value = signUpState.password,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    onValueChange = { onAction(SignUpAction.UpdatePassword(it)) },
                    label = { Text("Password") },
                    modifier = Modifier,
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible) {
                                    painterResource(id = R.drawable.ic_visibility_on)
                                } else {
                                    painterResource(id = R.drawable.ic_visibility_off)
                                }, contentDescription = "Visibility toggle"
                            )
                        }
                    })

                Button(onClick = { onAction(SignUpAction.SignUp) }) {
                    Text(text = "Sign up")
                }
            }
        }
    }
}
