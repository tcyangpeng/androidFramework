package com.example.core.network.api

import com.example.core.network.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Weather API interface for OpenWeatherMap.
 * Documentation: https://openweathermap.org/current
 */
interface WeatherApi {

    /**
     * Gets current weather by city name.
     * @param cityName The city name to search for
     * @param apiKey The API key for authentication
     * @param units Units of measurement (standard, metric, imperial)
     * @return WeatherResponse containing current weather data
     */
    @GET("weather")
    suspend fun getCurrentWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    /**
     * Gets current weather by geographic coordinates.
     * @param lat Latitude
     * @param lon Longitude
     * @param apiKey The API key for authentication
     * @param units Units of measurement (standard, metric, imperial)
     * @return WeatherResponse containing current weather data
     */
    @GET("weather")
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}
