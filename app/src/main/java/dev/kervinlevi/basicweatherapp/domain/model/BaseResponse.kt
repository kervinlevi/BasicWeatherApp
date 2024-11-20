package dev.kervinlevi.basicweatherapp.domain.model

/**
 * Created by kervinlevi on 20/11/24
 */
sealed interface ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>
    data class Error<T>(val exception: Exception) : ApiResponse<T>
}
