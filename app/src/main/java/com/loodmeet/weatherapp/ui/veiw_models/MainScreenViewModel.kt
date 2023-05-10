package com.loodmeet.weatherapp.ui.veiw_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.models.UnitOfMeasurement
import com.loodmeet.weatherapp.domain.FetchWeatherUseCase
import com.loodmeet.weatherapp.ui.models.HourlyWeather
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainScreenViewModel(private val fetchWeatherUseCase: FetchWeatherUseCase) : ViewModel() {

    var weatherData = mutableStateOf<Weather?>(null)
    fun fetchWeather() {
        viewModelScope.launch {
            delay(500)
            weatherData.value = fetchWeatherUseCase.fetchWeather()
            changeWindSpeedUnit(unit = UnitOfMeasurement.WindSpeedUnit.MetresPerSecond)
        }
    }

    fun changeWindSpeedUnit(unit: UnitOfMeasurement.WindSpeedUnit) {
        weatherData.value = weatherData.value?.copy(windSpeedUnit = unit)
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