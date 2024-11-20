package dev.kervinlevi.basicweatherapp.presentation.authentication.signup

/**
 * Created by kervinlevi on 20/11/24
 */
data class SignUpState(
    val isSigningUp: Boolean = false,
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val emailError: SignUpFieldError? = null,
    val nameError: SignUpFieldError? = null,
    val passwordError: SignUpFieldError? = null,
    val hasSignedUp: Boolean = false
)

sealed interface SignUpAction {
    object SignUp: SignUpAction
    data class UpdateEmail(val typedEmail: String): SignUpAction

    data class UpdateName(val typedName: String): SignUpAction

    data class UpdatePassword(val typedPassword: String): SignUpAction
}

sealed interface SignUpFieldError {
    object EmailWrongFormat: SignUpFieldError
    object EmailEmpty: SignUpFieldError

    object NameEmpty: SignUpFieldError
    object PasswordWrongFormat: SignUpFieldError
    object PasswordEmpty: SignUpFieldError
}
