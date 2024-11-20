package dev.kervinlevi.basicweatherapp.data.model

/**
 * Created by kervinlevi on 20/11/24
 */
data class AuthenticationResult(
    val accessToken: String,
    val refreshToken: String,
    val email: String,
    val name: String
)
