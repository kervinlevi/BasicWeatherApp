package dev.kervinlevi.basicweatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by kervinlevi on 20/11/24
 */

@Database(entities = [PastWeatherEntity::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun pastWeatherDao(): PastWeatherDao
}
