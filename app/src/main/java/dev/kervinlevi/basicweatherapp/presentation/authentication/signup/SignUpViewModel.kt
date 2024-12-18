package dev.kervinlevi.basicweatherapp.presentation.authentication.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kervinlevi.basicweatherapp.domain.authentication.AuthenticationRepository
import dev.kervinlevi.basicweatherapp.domain.model.ApiResponse
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginViewModel.Companion.EMAIL_REGEX
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by kervinlevi on 20/11/24
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    var signUpState = mutableStateOf(SignUpState())
        private set

    fun onAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.SignUp -> {
                signUp()
            }

            is SignUpAction.UpdateEmail -> {
                signUpState.value = signUpState.value.copy(
                    email = action.typedEmail, emailError = validateEmail(action.typedEmail)
                )
            }

            is SignUpAction.UpdateName -> {
                signUpState.value = signUpState.value.copy(
                    name = action.typedName, nameError = validateName(action.typedName)
                )
            }

            is SignUpAction.UpdatePassword -> {
                signUpState.value = signUpState.value.copy(
                    password = action.typedPassword,
                    passwordError = validatePassword(action.typedPassword)
                )
            }
        }
    }

    private fun validateEmail(typedEmail: String): SignUpFieldError? {
        return if (typedEmail.isEmpty()) {
            SignUpFieldError.EmailEmpty
        } else if (!EMAIL_REGEX.matches(typedEmail)) {
            SignUpFieldError.EmailWrongFormat
        } else {
            null
        }
    }

    private fun validateName(typedName: String): SignUpFieldError? {
        return if (typedName.isEmpty()) {
            SignUpFieldError.NameEmpty
        } else {
            null
        }
    }

    private fun validatePassword(typedPassword: String): SignUpFieldError? {
        return if (typedPassword.isEmpty()) {
            SignUpFieldError.PasswordEmpty
        } else if (typedPassword.length < 8 ||
            !typedPassword.contains("[0-9]".toRegex()) ||
            !typedPassword.contains("[a-z]".toRegex()) ||
            !typedPassword.contains("[A-Z]".toRegex())
        ) {
            SignUpFieldError.PasswordWrongFormat
        } else {
            null
        }
    }

    private fun signUp() = viewModelScope.launch {
        if (signUpState.value.isSigningUp) return@launch

        signUpState.value = signUpState.value.copy(isSigningUp = true)
        signUpState.value.let { currentState ->
            signUpState.value = currentState.copy(
                emailError = validateEmail(currentState.email),
                nameError = validateName(currentState.name),
                passwordError = validatePassword(currentState.password)
            )
            if (signUpState.value.emailError != null ||
                signUpState.value.nameError != null ||
                signUpState.value.passwordError != null
            ) {
                signUpState.value = signUpState.value.copy(isSigningUp = false)
                return@launch
            }

            val result = authenticationRepository.signUp(
                currentState.email,
                currentState.name,
                currentState.password
            )
            signUpState.value = if (result is ApiResponse.Success) {
                signUpState.value.copy(isSigningUp = false, hasSignedUp = true)
            } else {
                signUpState.value.copy(isSigningUp = false, hasSignedUp = false)
            }
        }
    }
}
