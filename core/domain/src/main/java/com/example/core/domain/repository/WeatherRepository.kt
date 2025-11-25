package com.example.core.domain.repository

import com.example.core.common.result.Result
import com.example.core.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for weather data.
 * Follows the Repository pattern for unidirectional data flow.
 */
interface WeatherRepository {

    /**
     * Gets current weather for a city.
     * @param cityName The name of the city
     * @param forceRefresh Whether to force refresh from network
     * @return Flow of Result containing WeatherInfo
     */
    fun getWeatherByCity(cityName: String, forceRefresh: Boolean = false): Flow<Result<WeatherInfo>>

    /**
     * Gets current weather by coordinates.
     * @param lat Latitude
     * @param lon Longitude
     * @return Flow of Result containing WeatherInfo
     */
    fun getWeatherByCoordinates(lat: Double, lon: Double): Flow<Result<WeatherInfo>>

    /**
     * Gets cached weather data for a city.
     * @param cityName The name of the city
     * @return Flow of WeatherInfo or null if not cached
     */
    fun getCachedWeather(cityName: String): Flow<WeatherInfo?>

    /**
     * Gets all cached weather data.
     * @return Flow of list of WeatherInfo
     */
    fun getAllCachedWeather(): Flow<List<WeatherInfo>>

    /**
     * Clears all cached weather data.
     */
    suspend fun clearCache()
}
