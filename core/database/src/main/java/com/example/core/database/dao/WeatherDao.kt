package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for weather data.
 */
@Dao
interface WeatherDao {

    /**
     * Inserts or updates weather data for a city.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    /**
     * Gets weather data for a specific city.
     */
    @Query("SELECT * FROM weather_cache WHERE cityName = :cityName")
    suspend fun getWeatherByCity(cityName: String): WeatherEntity?

    /**
     * Gets weather data for a specific city as a Flow.
     */
    @Query("SELECT * FROM weather_cache WHERE cityName = :cityName")
    fun getWeatherByCityFlow(cityName: String): Flow<WeatherEntity?>

    /**
     * Gets all cached weather data.
     */
    @Query("SELECT * FROM weather_cache ORDER BY updatedAt DESC")
    fun getAllWeather(): Flow<List<WeatherEntity>>

    /**
     * Deletes weather data for a specific city.
     */
    @Query("DELETE FROM weather_cache WHERE cityName = :cityName")
    suspend fun deleteWeatherByCity(cityName: String)

    /**
     * Deletes all cached weather data.
     */
    @Query("DELETE FROM weather_cache")
    suspend fun deleteAllWeather()

    /**
     * Deletes old weather data (older than specified time).
     */
    @Query("DELETE FROM weather_cache WHERE updatedAt < :timestamp")
    suspend fun deleteOldWeather(timestamp: Long)
}
