package com.example.feature.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.example.core.common.extension.gone
import com.example.core.common.extension.showToast
import com.example.core.common.extension.visible
import com.example.core.common.util.DateUtils
import com.example.core.ui.base.BaseFragment
import com.example.feature.weather.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for displaying weather information.
 */
@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherBinding {
        return FragmentWeatherBinding.inflate(inflater, container, false)
    }

    override fun setupViews() {
        setupSearchInput()
        setupSwipeRefresh()
    }

    private fun setupSearchInput() {
        binding.etCitySearch.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val cityName = textView.text.toString().trim()
                if (cityName.isNotEmpty()) {
                    viewModel.setEvent(WeatherEvent.UpdateCity(cityName))
                }
                true
            } else {
                false
            }
        }

        binding.btnSearch.setOnClickListener {
            val cityName = binding.etCitySearch.text.toString().trim()
            if (cityName.isNotEmpty()) {
                viewModel.setEvent(WeatherEvent.UpdateCity(cityName))
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.setEvent(WeatherEvent.Refresh)
        }
    }

    override fun observeData() {
        viewModel.uiState.collectWhenStarted { state ->
            updateUi(state)
        }

        viewModel.effect.collectWhenStarted { effect ->
            handleEffect(effect)
        }
    }

    private fun updateUi(state: WeatherUiState) {
        binding.swipeRefresh.isRefreshing = state.isLoading

        if (state.isLoading) {
            binding.progressBar.visible()
            binding.weatherContent.gone()
            binding.tvError.gone()
        } else if (state.error != null) {
            binding.progressBar.gone()
            binding.weatherContent.gone()
            binding.tvError.visible()
            binding.tvError.text = state.error
        } else {
            binding.progressBar.gone()
            binding.tvError.gone()
            binding.weatherContent.visible()

            state.weatherInfo?.let { weather ->
                binding.apply {
                    tvCityName.text = "${weather.cityName}, ${weather.country}"
                    tvTemperature.text = weather.getFormattedTemperature()
                    tvDescription.text = weather.description.replaceFirstChar { it.uppercase() }
                    tvFeelsLike.text = "Feels like: ${weather.getFormattedFeelsLike()}"
                    tvTempRange.text = weather.getFormattedTempRange()
                    tvHumidity.text = "Humidity: ${weather.getFormattedHumidity()}"
                    tvWind.text = "Wind: ${weather.getFormattedWindSpeed()}"
                    tvPressure.text = "Pressure: ${weather.pressure} hPa"
                    tvSunrise.text = "Sunrise: ${DateUtils.formatDisplayTime(weather.sunrise * 1000)}"
                    tvSunset.text = "Sunset: ${DateUtils.formatDisplayTime(weather.sunset * 1000)}"
                    tvLastUpdated.text = "Updated: ${DateUtils.formatDateTime(weather.updatedAt)}"
                }
            }
        }
    }

    private fun handleEffect(effect: WeatherEffect) {
        when (effect) {
            is WeatherEffect.ShowError -> showToast(effect.message)
            is WeatherEffect.ShowToast -> showToast(effect.message)
        }
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}
