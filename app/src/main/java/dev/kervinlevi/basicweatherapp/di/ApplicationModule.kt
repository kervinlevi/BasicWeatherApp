package dev.kervinlevi.basicweatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kervinlevi.basicweatherapp.data.location.LocationProviderImpl
import dev.kervinlevi.basicweatherapp.domain.location.LocationProvider

/**
 * Created by kervinlevi on 19/11/24
 */

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideLocationProvider(@ApplicationContext context: Context): LocationProvider {
        return LocationProviderImpl(context)
    }
}
