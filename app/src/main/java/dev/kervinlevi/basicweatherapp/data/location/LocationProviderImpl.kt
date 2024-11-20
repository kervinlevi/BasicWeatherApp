package dev.kervinlevi.basicweatherapp.data.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.core.content.ContextCompat.checkSelfPermission
import com.google.android.gms.location.LocationServices
import dev.kervinlevi.basicweatherapp.domain.location.LocationProvider
import dev.kervinlevi.basicweatherapp.domain.model.Location
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by kervinlevi on 19/11/24
 */
class LocationProviderImpl @Inject constructor(private val context: Context) : LocationProvider {
    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Location? {
        if (!isLocationPermissionGranted()) {
            return null
        }

        return suspendCoroutine { coroutine ->
            val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            with(locationProviderClient.lastLocation) {
                addOnSuccessListener { fuseLocation ->
                    val geocoder = Geocoder(context, Locale.getDefault())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        geocoder.getFromLocation(fuseLocation.latitude,
                            fuseLocation.longitude,
                            1,
                            object : Geocoder.GeocodeListener {
                                override fun onGeocode(addresses: MutableList<Address>) {
                                    coroutine.resume(
                                        getLocation(
                                            fuseLocation, addresses.firstOrNull()
                                        )
                                    )
                                }

                                override fun onError(errorMessage: String?) {
                                    coroutine.resume(getLocation(fuseLocation, null))
                                }
                            })
                    } else {
                        val addresses = geocoder.getFromLocation(
                            fuseLocation.latitude, fuseLocation.longitude, 1
                        )
                        coroutine.resume(getLocation(fuseLocation, addresses?.firstOrNull()))
                    }
                }
                addOnFailureListener {
                    coroutine.resume(null)
                }
                addOnCanceledListener {
                    coroutine.resume(null)
                }
            }
        }
    }

    override fun isLocationPermissionGranted(): Boolean {
        return checkSelfPermission(
            context, ACCESS_FINE_LOCATION
        ) == PERMISSION_GRANTED || checkSelfPermission(
            context, ACCESS_COARSE_LOCATION
        ) == PERMISSION_GRANTED
    }

    private fun getLocation(
        androidLocation: android.location.Location, address: Address?
    ): Location {
        return Location(
            latitude = androidLocation.latitude,
            longitude = androidLocation.longitude,
            city = address?.locality,
            country = address?.countryName
        )
    }
}
