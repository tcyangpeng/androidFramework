package com.example.feature.weather.ui

import com.example.core.domain.model.WeatherInfo
import com.example.core.ui.base.UiEffect
import com.example.core.ui.base.UiEvent
import com.example.core.ui.base.UiState

/**
 * UI State for Weather screen.
 */
data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val error: String? = null,
    val cityName: String = "London"
) : UiState

/**
 * UI Events for Weather screen.
 */
sealed class WeatherEvent : UiEvent {
    data class LoadWeather(val cityName: String, val forceRefresh: Boolean = false) : WeatherEvent()
    data class UpdateCity(val cityName: String) : WeatherEvent()
    data object Refresh : WeatherEvent()
}

/**
 * UI Effects for Weather screen.
 */
sealed class WeatherEffect : UiEffect {
    data class ShowError(val message: String) : WeatherEffect()
    data class ShowToast(val message: String) : WeatherEffect()
}
