package com.example.feature.weather

import app.cash.turbine.test
import com.example.core.common.result.Result
import com.example.core.datastore.UserPreferencesManager
import com.example.core.domain.model.WeatherInfo
import com.example.core.domain.usecase.GetWeatherUseCase
import com.example.feature.weather.ui.WeatherEffect
import com.example.feature.weather.ui.WeatherEvent
import com.example.feature.weather.ui.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var preferencesManager: UserPreferencesManager

    private val testWeatherInfo = WeatherInfo(
        cityName = "London",
        country = "GB",
        temperature = 15.0,
        feelsLike = 14.0,
        tempMin = 12.0,
        tempMax = 18.0,
        humidity = 65,
        pressure = 1013,
        description = "Cloudy",
        iconUrl = "https://example.com/icon.png",
        windSpeed = 5.5,
        sunrise = 1705039200L,
        sunset = 1705071600L,
        updatedAt = System.currentTimeMillis()
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getWeatherUseCase = mock(GetWeatherUseCase::class.java)
        preferencesManager = mock(UserPreferencesManager::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WeatherInfo getFormattedTemperature returns correct format`() {
        val weatherInfo = testWeatherInfo
        assertEquals("15°C", weatherInfo.getFormattedTemperature())
    }

    @Test
    fun `WeatherInfo getFormattedTempRange returns correct format`() {
        val weatherInfo = testWeatherInfo
        assertEquals("H:18°C L:12°C", weatherInfo.getFormattedTempRange())
    }

    @Test
    fun `WeatherInfo getFormattedHumidity returns correct format`() {
        val weatherInfo = testWeatherInfo
        assertEquals("65%", weatherInfo.getFormattedHumidity())
    }

    @Test
    fun `WeatherInfo getFormattedWindSpeed returns correct format`() {
        val weatherInfo = testWeatherInfo
        assertEquals("5.5 m/s", weatherInfo.getFormattedWindSpeed())
    }
}
