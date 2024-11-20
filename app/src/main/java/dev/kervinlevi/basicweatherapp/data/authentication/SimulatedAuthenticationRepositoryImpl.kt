package dev.kervinlevi.basicweatherapp.data.authentication

import android.content.SharedPreferences
import dev.kervinlevi.basicweatherapp.data.model.AuthenticationResult
import dev.kervinlevi.basicweatherapp.domain.authentication.AuthenticationRepository
import dev.kervinlevi.basicweatherapp.domain.model.ApiResponse
import dev.kervinlevi.basicweatherapp.domain.model.User
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Created by kervinlevi on 20/11/24
 */
class SimulatedAuthenticationRepositoryImpl(
    private val encryptedSharedPreferences: SharedPreferences
) : AuthenticationRepository {
    override suspend fun logIn(email: String, password: String): ApiResponse<User> {
        val authenticationResult = simulateApiCall(email)
        saveTokens(authenticationResult)
        return ApiResponse.Success(User(authenticationResult.email, authenticationResult.name))
    }

    override suspend fun signUp(email: String, name: String, password: String): ApiResponse<User> {
        val authenticationResult = simulateApiCall(email, name)
        saveTokens(authenticationResult)
        return ApiResponse.Success(User(authenticationResult.email, authenticationResult.name))
    }

    override fun isLoggedIn(): Boolean {
        return encryptedSharedPreferences.getString(KEY_A_TOKEN, null) != null &&
                encryptedSharedPreferences.getString(KEY_R_TOKEN, null) != null
    }

    override suspend fun logOut() {
        with(encryptedSharedPreferences.edit()) {
            remove(KEY_A_TOKEN)
            remove(KEY_R_TOKEN)
            apply()
        }
    }

    private suspend fun simulateApiCall(email: String, name: String? = null): AuthenticationResult {
        delay(2000L)
        return AuthenticationResult(
            accessToken = Random.nextDouble().toString(),
            refreshToken = Random.nextDouble().toString(),
            email = email,
            name = name ?: "User"
        )
    }

    private fun saveTokens(authenticationResult: AuthenticationResult) {
        with(encryptedSharedPreferences.edit()) {
            putString(KEY_A_TOKEN, authenticationResult.accessToken)
            putString(KEY_R_TOKEN, authenticationResult.refreshToken)
            apply()
        }
    }

    companion object {
        private const val KEY_A_TOKEN = "shared_pref_key_a_token"
        private const val KEY_R_TOKEN = "shared_pref_key_r_token"
    }
}
