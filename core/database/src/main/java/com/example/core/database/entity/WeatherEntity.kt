package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing cached weather data in the database.
 */
@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val pressure: Int,
    val description: String,
    val icon: String,
    val windSpeed: Double,
    val country: String,
    val sunrise: Long,
    val sunset: Long,
    val updatedAt: Long = System.currentTimeMillis()
)
