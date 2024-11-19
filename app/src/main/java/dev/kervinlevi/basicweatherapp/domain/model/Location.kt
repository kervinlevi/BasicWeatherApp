package dev.kervinlevi.basicweatherapp.domain.model

/**
 * Created by kervinlevi on 19/11/24
 */
data class Location(
    val latitude: Double,
    val longitude: Double,
    val city: String?,
    val country: String?
)
