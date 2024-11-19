package dev.kervinlevi.basicweatherapp.domain.location

import dev.kervinlevi.basicweatherapp.domain.model.Location

/**
 * Created by kervinlevi on 19/11/24
 */
interface LocationProvider {
    suspend fun getLocation(): Location?

    fun isLocationPermissionGranted(): Boolean
}
