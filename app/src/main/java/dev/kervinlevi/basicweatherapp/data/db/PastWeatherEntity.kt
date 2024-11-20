package dev.kervinlevi.basicweatherapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.kervinlevi.basicweatherapp.domain.model.WeatherCondition

/**
 * Created by kervinlevi on 20/11/24
 */

@Entity(indices = [Index(value = ["timestamp", "city", "country"], unique = true)])
data class PastWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "condition") val condition: WeatherCondition,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "sunriseTimestamp") val sunriseTimestamp: Long,
    @ColumnInfo(name = "sunsetTimestamp") val sunsetTimestamp: Long,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "description") val description: String? = null
)
