package com.loodmeet.weatherapp.ui.veiw_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.domain.use_case.FetchWeatherUseCase
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel(private val fetchWeatherUseCase: FetchWeatherUseCase) : ViewModel() {

    var weatherData = mutableStateOf<List<Weather>>(listOf())
    var isLoading = mutableStateOf(true)
    private var measurementUnitsSet = MeasurementUnitsSet()
    private lateinit var location: Location

    init {
        location = Location.Moscow
    }
    fun fetchWeather() {
        viewModelScope.launch {
            isLoading.value = true
            weatherData.value = fetchWeatherUseCase.fetchWeather(measurementUnitsSet)
            isLoading.value = false
        }
    }

    fun fetchCurrentMeasurementUnitsSet(): MeasurementUnitsSet {
        return measurementUnitsSet
    }
    fun changeMeasurementUnitsSet(set: MeasurementUnitsSet) {
        measurementUnitsSet = set
        fetchWeather()
    }
    fun fetchCurrentLocation(): Location {
        return location
    }

    fun changeLocation(location: Location) {
        this.location = location
        fetchWeather()
    }

    fun fetchLocationList(): List<Location> {
        return Location.getList()
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

fun main() {
}