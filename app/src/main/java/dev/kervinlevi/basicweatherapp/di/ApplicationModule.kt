package dev.kervinlevi.basicweatherapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.kervinlevi.basicweatherapp.data.authentication.SimulatedAuthenticationRepositoryImpl
import dev.kervinlevi.basicweatherapp.data.db.PastWeatherDao
import dev.kervinlevi.basicweatherapp.data.db.WeatherDatabase
import dev.kervinlevi.basicweatherapp.data.location.LocationProviderImpl
import dev.kervinlevi.basicweatherapp.data.weather.WeatherApi
import dev.kervinlevi.basicweatherapp.data.weather.WeatherRepositoryImpl
import dev.kervinlevi.basicweatherapp.domain.authentication.AuthenticationRepository
import dev.kervinlevi.basicweatherapp.domain.location.LocationProvider
import dev.kervinlevi.basicweatherapp.domain.weather.WeatherRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

    @Provides
    fun provideWeatherApi(): WeatherApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create(WeatherApi::class.java)
    }

    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
        pastWeatherDao: PastWeatherDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi, pastWeatherDao)
    }

    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_db").build()
    }

    @Provides
    fun providePastWeatherDao(weatherDatabase: WeatherDatabase): PastWeatherDao {
        return weatherDatabase.pastWeatherDao()
    }

    @Provides
    @EncryptedSharedPrefs
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences
    }

    @Provides
    fun provideAuthenticationRepository(
        @EncryptedSharedPrefs encryptedSharedPrefs: SharedPreferences
    ): AuthenticationRepository {
        return SimulatedAuthenticationRepositoryImpl(encryptedSharedPrefs)
    }
}
