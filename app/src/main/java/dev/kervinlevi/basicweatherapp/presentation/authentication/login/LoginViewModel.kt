package dev.kervinlevi.basicweatherapp.presentation.authentication.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kervinlevi.basicweatherapp.domain.authentication.AuthenticationRepository
import dev.kervinlevi.basicweatherapp.domain.model.ApiResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by kervinlevi on 20/11/24
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authorizationRepository: AuthenticationRepository
) : ViewModel() {

    var loginState = mutableStateOf(LoginState())
        private set

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.LogIn -> {
                logIn()
            }

            is LoginAction.UpdateEmail -> {
                loginState.value = loginState.value.copy(
                    email = action.typedEmail, emailError = validateEmail(action.typedEmail)
                )
            }

            is LoginAction.UpdatePassword -> {
                loginState.value = loginState.value.copy(
                    password = action.typedPassword,
                    passwordError = validatePassword(action.typedPassword)
                )
            }
        }
    }

    private fun validateEmail(typedEmail: String): LoginFieldError? {
        return if (typedEmail.isEmpty()) {
            LoginFieldError.EmailEmpty
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(typedEmail).matches()) {
            LoginFieldError.EmailWrongFormat
        } else {
            null
        }
    }

    private fun validatePassword(typedPassword: String): LoginFieldError? {
        return if (typedPassword.isEmpty()) {
            LoginFieldError.PasswordEmpty
        } else if (!typedPassword.contains("[0-9]".toRegex()) ||
            !typedPassword.contains("[a-z]".toRegex()) ||
            !typedPassword.contains("[A-Z]".toRegex())
        ) {
            LoginFieldError.PasswordWrongFormat
        } else {
            null
        }
    }

    private fun logIn() = viewModelScope.launch {
        if (loginState.value.isLoggingIn) return@launch

        loginState.value = loginState.value.copy(isLoggingIn = true)
        loginState.value.let { currentState ->
            loginState.value = currentState.copy(
                emailError = validateEmail(currentState.email),
                passwordError = validatePassword(currentState.password)
            )
            if (loginState.value.emailError != null || loginState.value.passwordError != null) {
                return@launch
            }

            val result = authorizationRepository.logIn(currentState.email, currentState.password)
            loginState.value = if (result is ApiResponse.Success) {
                loginState.value.copy(isLoggingIn = false, hasLoggedIn = true)
            } else {
                loginState.value.copy(isLoggingIn = false, hasLoggedIn = false)
            }
        }
    }
}
