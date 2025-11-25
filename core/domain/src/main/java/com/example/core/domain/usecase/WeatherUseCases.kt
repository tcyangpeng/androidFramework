package com.example.core.domain.usecase

import com.example.core.common.result.Result
import com.example.core.domain.model.WeatherInfo
import com.example.core.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting weather information.
 * Encapsulates business logic for weather data retrieval.
 */
class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    /**
     * Gets current weather for a city.
     * @param cityName The name of the city
     * @param forceRefresh Whether to force refresh from network
     * @return Flow of Result containing WeatherInfo
     */
    operator fun invoke(cityName: String, forceRefresh: Boolean = false): Flow<Result<WeatherInfo>> {
        return weatherRepository.getWeatherByCity(cityName, forceRefresh)
    }
}

/**
 * Use case for getting weather by coordinates.
 */
class GetWeatherByCoordinatesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    /**
     * Gets current weather by coordinates.
     * @param lat Latitude
     * @param lon Longitude
     * @return Flow of Result containing WeatherInfo
     */
    operator fun invoke(lat: Double, lon: Double): Flow<Result<WeatherInfo>> {
        return weatherRepository.getWeatherByCoordinates(lat, lon)
    }
}

/**
 * Use case for getting cached weather data.
 */
class GetCachedWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    /**
     * Gets all cached weather data.
     * @return Flow of list of WeatherInfo
     */
    operator fun invoke(): Flow<List<WeatherInfo>> {
        return weatherRepository.getAllCachedWeather()
    }
}

/**
 * Use case for clearing weather cache.
 */
class ClearWeatherCacheUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    /**
     * Clears all cached weather data.
     */
    suspend operator fun invoke() {
        weatherRepository.clearCache()
    }
}
