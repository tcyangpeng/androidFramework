package com.example.feature.weather.data.mapper

import com.example.core.database.entity.WeatherEntity
import com.example.core.domain.model.WeatherInfo
import com.example.core.network.model.WeatherResponse

/**
 * Mapper functions for converting between data models.
 */

/**
 * Converts WeatherResponse from network to WeatherInfo domain model.
 */
fun WeatherResponse.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        cityName = name ?: "",
        country = sys?.country ?: "",
        temperature = main?.temp ?: 0.0,
        feelsLike = main?.feelsLike ?: 0.0,
        tempMin = main?.tempMin ?: 0.0,
        tempMax = main?.tempMax ?: 0.0,
        humidity = main?.humidity ?: 0,
        pressure = main?.pressure ?: 0,
        description = weather?.firstOrNull()?.description ?: "",
        iconUrl = weather?.firstOrNull()?.icon?.let { 
            "https://openweathermap.org/img/wn/${it}@2x.png" 
        } ?: "",
        windSpeed = wind?.speed ?: 0.0,
        sunrise = sys?.sunrise ?: 0L,
        sunset = sys?.sunset ?: 0L,
        updatedAt = System.currentTimeMillis()
    )
}

/**
 * Converts WeatherInfo domain model to WeatherEntity for database storage.
 */
fun WeatherInfo.toEntity(): WeatherEntity {
    return WeatherEntity(
        cityName = cityName,
        temperature = temperature,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        humidity = humidity,
        pressure = pressure,
        description = description,
        icon = iconUrl,
        windSpeed = windSpeed,
        country = country,
        sunrise = sunrise,
        sunset = sunset,
        updatedAt = updatedAt
    )
}

/**
 * Converts WeatherEntity from database to WeatherInfo domain model.
 */
fun WeatherEntity.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        cityName = cityName,
        country = country,
        temperature = temperature,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        humidity = humidity,
        pressure = pressure,
        description = description,
        iconUrl = icon,
        windSpeed = windSpeed,
        sunrise = sunrise,
        sunset = sunset,
        updatedAt = updatedAt
    )
}
