package com.loodmeet.weatherapp.ui.veiw_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.loodmeet.weatherapp.core.models.MeasurementUnit
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.domain.FetchWeatherUseCase
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel(private val fetchWeatherUseCase: FetchWeatherUseCase) : ViewModel() {

    var weatherData = mutableStateOf<Weather?>(null)
    var isLoading = mutableStateOf<Boolean>(true)
    var measurementUnitsSet = MeasurementUnitsSet()
    fun fetchWeather() {
        viewModelScope.launch {
            isLoading.value = true
            delay(500)
            weatherData.value = fetchWeatherUseCase.fetchWeather(measurementUnitsSet)
            isLoading.value = false
        }
    }

    fun fetchCurrentMeasurementUnitsSet(): MeasurementUnitsSet {
        Config.LOG.logDebug(measurementUnitsSet.toString())
        return measurementUnitsSet
    }
    fun changeMeasurementUnitsSet(set: MeasurementUnitsSet) {
        measurementUnitsSet = set
        fetchWeather()
    }

    class Factory @Inject constructor(
        private val fetchWeatherUseCase: FetchWeatherUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainScreenViewModel(
                fetchWeatherUseCase = fetchWeatherUseCase,
            ) as T
        }
    }
}