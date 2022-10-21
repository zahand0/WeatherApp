package com.example.weather.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.location.LocationTracker
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    companion object {
        private const val TAG = "WeatherViewModel"
    }
    
    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result = repository.getWeatherData(
                    location.latitude,
                    location.longitude
                )) {
                    is Resource.Error -> {
                        Log.d("ViewModel", "loadWeatherInfo: FAILED INTERNET")
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } ?: kotlin.run {
                Log.d("ViewModel", "loadWeatherInfo: GPS NOT ENABLED")
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retriver location. Make sure to grant permission and enable GPS."
                )
            }

        }
    }
}