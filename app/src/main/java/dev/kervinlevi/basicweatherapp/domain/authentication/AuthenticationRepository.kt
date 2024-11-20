package dev.kervinlevi.basicweatherapp.domain.authentication

import dev.kervinlevi.basicweatherapp.domain.model.ApiResponse
import dev.kervinlevi.basicweatherapp.domain.model.User

/**
 * Created by kervinlevi on 20/11/24
 */
interface AuthenticationRepository {

    suspend fun logIn(email: String, password: String): ApiResponse<User>

    suspend fun signUp(email: String, name: String, password: String): ApiResponse<User>

    fun isLoggedIn(): Boolean

    suspend fun logOut(): Unit
}
