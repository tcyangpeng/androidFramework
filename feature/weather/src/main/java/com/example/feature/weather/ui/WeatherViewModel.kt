package com.example.feature.weather.ui

import androidx.lifecycle.viewModelScope
import com.example.core.common.result.Result
import com.example.core.datastore.UserPreferencesManager
import com.example.core.domain.usecase.GetWeatherUseCase
import com.example.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Weather screen.
 * Implements MVI pattern with unidirectional data flow.
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val preferencesManager: UserPreferencesManager
) : BaseViewModel<WeatherUiState, WeatherEvent, WeatherEffect>() {

    override fun createInitialState(): WeatherUiState = WeatherUiState()

    init {
        loadInitialCity()
    }

    private fun loadInitialCity() {
        viewModelScope.launch {
            val defaultCity = preferencesManager.defaultCityFlow.first()
            setState { copy(cityName = defaultCity) }
            loadWeather(defaultCity, forceRefresh = false)
        }
    }

    override fun handleEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.LoadWeather -> loadWeather(event.cityName, event.forceRefresh)
            is WeatherEvent.UpdateCity -> updateCity(event.cityName)
            is WeatherEvent.Refresh -> loadWeather(currentState.cityName, forceRefresh = true)
        }
    }

    private fun loadWeather(cityName: String, forceRefresh: Boolean) {
        viewModelScope.launch {
            getWeatherUseCase(cityName, forceRefresh).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true, error = null) }
                    }
                    is Result.Success -> {
                        setState { 
                            copy(
                                isLoading = false, 
                                weatherInfo = result.data, 
                                error = null 
                            ) 
                        }
                    }
                    is Result.Error -> {
                        setState { 
                            copy(
                                isLoading = false, 
                                error = result.message ?: "Unknown error occurred"
                            ) 
                        }
                        setEffect(WeatherEffect.ShowError(result.message ?: "Failed to load weather"))
                    }
                }
            }
        }
    }

    private fun updateCity(cityName: String) {
        viewModelScope.launch {
            setState { copy(cityName = cityName) }
            preferencesManager.setDefaultCity(cityName)
            loadWeather(cityName, forceRefresh = true)
        }
    }
}
