package dev.kervinlevi.basicweatherapp.presentation.authentication.login

/**
 * Created by kervinlevi on 20/11/24
 */
data class LoginState(
    val isLoggingIn: Boolean = false,
    val email: String = "",
    val password: String = "",
    val emailError: LoginFieldError? = null,
    val passwordError: LoginFieldError? = null,
    val hasLoggedIn: Boolean = false
)

sealed interface LoginAction {
    object LogIn: LoginAction
    data class UpdateEmail(val typedEmail: String): LoginAction

    data class UpdatePassword(val typedPassword: String): LoginAction
}

sealed interface LoginFieldError {
    object EmailWrongFormat: LoginFieldError
    object EmailEmpty: LoginFieldError
    object PasswordWrongFormat: LoginFieldError
    object PasswordEmpty: LoginFieldError
}
