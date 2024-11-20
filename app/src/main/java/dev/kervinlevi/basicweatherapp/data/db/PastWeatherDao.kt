package dev.kervinlevi.basicweatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by kervinlevi on 20/11/24
 */

@Dao
interface PastWeatherDao {
    @Query("SELECT * FROM pastweatherentity ORDER BY timestamp DESC LIMIT 20 OFFSET 1")
    suspend fun getMostRecent(): List<PastWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pastWeatherEntity: PastWeatherEntity)

    @Query("DELETE FROM pastweatherentity")
    suspend fun clear()
}
