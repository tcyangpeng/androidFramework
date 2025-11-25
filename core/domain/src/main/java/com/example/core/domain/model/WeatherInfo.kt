package com.example.core.domain.model

/**
 * Domain model for weather data.
 * This is the clean domain representation used across the app.
 */
data class WeatherInfo(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val pressure: Int,
    val description: String,
    val iconUrl: String,
    val windSpeed: Double,
    val sunrise: Long,
    val sunset: Long,
    val updatedAt: Long
) {
    /**
     * Returns formatted temperature string.
     */
    fun getFormattedTemperature(unit: String = "°C"): String {
        return "${temperature.toInt()}$unit"
    }
    
    /**
     * Returns formatted feels like temperature string.
     */
    fun getFormattedFeelsLike(unit: String = "°C"): String {
        return "${feelsLike.toInt()}$unit"
    }
    
    /**
     * Returns formatted temperature range string.
     */
    fun getFormattedTempRange(unit: String = "°C"): String {
        return "H:${tempMax.toInt()}$unit L:${tempMin.toInt()}$unit"
    }
    
    /**
     * Returns formatted humidity string.
     */
    fun getFormattedHumidity(): String {
        return "$humidity%"
    }
    
    /**
     * Returns formatted wind speed string.
     */
    fun getFormattedWindSpeed(unit: String = "m/s"): String {
        return "$windSpeed $unit"
    }
}
