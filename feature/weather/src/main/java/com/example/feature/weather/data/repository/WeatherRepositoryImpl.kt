package com.example.feature.weather.data.repository

import com.example.core.common.result.Result
import com.example.core.database.dao.WeatherDao
import com.example.core.domain.model.WeatherInfo
import com.example.core.domain.repository.WeatherRepository
import com.example.core.network.BuildConfig
import com.example.core.network.api.WeatherApi
import com.example.feature.weather.data.mapper.toEntity
import com.example.feature.weather.data.mapper.toWeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation for weather data.
 * Implements the single source of truth pattern with local caching.
 */
@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    companion object {
        private const val CACHE_DURATION_MS = 30 * 60 * 1000L // 30 minutes
    }

    override fun getWeatherByCity(cityName: String, forceRefresh: Boolean): Flow<Result<WeatherInfo>> = flow {
        emit(Result.Loading)
        
        try {
            // Check cache first if not forcing refresh
            if (!forceRefresh) {
                val cached = weatherDao.getWeatherByCity(cityName)
                if (cached != null && !isCacheExpired(cached.updatedAt)) {
                    emit(Result.Success(cached.toWeatherInfo()))
                    return@flow
                }
            }
            
            // Fetch from network
            val response = weatherApi.getCurrentWeatherByCity(
                cityName = cityName,
                apiKey = BuildConfig.WEATHER_API_KEY
            )
            
            val weatherInfo = response.toWeatherInfo()
            
            // Cache the result
            weatherDao.insertWeather(weatherInfo.toEntity())
            
            emit(Result.Success(weatherInfo))
        } catch (e: Exception) {
            // Try to return cached data on error
            val cached = weatherDao.getWeatherByCity(cityName)
            if (cached != null) {
                emit(Result.Success(cached.toWeatherInfo()))
            } else {
                emit(Result.Error(exception = e, message = e.message))
            }
        }
    }

    override fun getWeatherByCoordinates(lat: Double, lon: Double): Flow<Result<WeatherInfo>> = flow {
        emit(Result.Loading)
        
        try {
            val response = weatherApi.getCurrentWeatherByCoordinates(
                lat = lat,
                lon = lon,
                apiKey = BuildConfig.WEATHER_API_KEY
            )
            
            val weatherInfo = response.toWeatherInfo()
            
            // Cache the result
            weatherDao.insertWeather(weatherInfo.toEntity())
            
            emit(Result.Success(weatherInfo))
        } catch (e: Exception) {
            emit(Result.Error(exception = e, message = e.message))
        }
    }

    override fun getCachedWeather(cityName: String): Flow<WeatherInfo?> {
        return weatherDao.getWeatherByCityFlow(cityName).map { entity ->
            entity?.toWeatherInfo()
        }
    }

    override fun getAllCachedWeather(): Flow<List<WeatherInfo>> {
        return weatherDao.getAllWeather().map { entities ->
            entities.map { it.toWeatherInfo() }
        }
    }

    override suspend fun clearCache() {
        weatherDao.deleteAllWeather()
    }

    private fun isCacheExpired(updatedAt: Long): Boolean {
        return System.currentTimeMillis() - updatedAt > CACHE_DURATION_MS
    }
}
