package com.loodmeet.weatherapp.ui.veiw_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.loodmeet.weatherapp.core.models.Language
import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.models.Settings
import com.loodmeet.weatherapp.core.models.Theme
import com.loodmeet.weatherapp.di.AppScope
import com.loodmeet.weatherapp.domain.use_case.*
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val fetchSettingsUseCase: FetchSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
) : ViewModel() {

    private val weatherData: MutableList<Weather> = mutableListOf()
    private val isLoading = mutableStateOf(true)
    private val isError = mutableStateOf(false)
    private val showNotificationFromOpenMeteo = mutableStateOf(false)
    private var settings = mutableStateOf(fetchSettingsUseCase.execute())

    fun getWeatherData(): List<Weather> = weatherData
    fun getIsLoading(): State<Boolean> = isLoading
    fun showNotificationFromOpenMeteo(): MutableState<Boolean> = showNotificationFromOpenMeteo
    fun getIsError(): State<Boolean> = isError
    fun getSettingsState(): State<Settings> = settings
    fun getSettings(): Settings = settings.value

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        viewModelScope.launch {
            isLoading.value = true

            var fromOpenMeteoFlag = false;

            try {
                settings.value = fetchSettingsUseCase.execute()

                val weather: List<Weather> = try {
                    fetchWeatherUseCase.execute()
                } catch (e: Exception) {
                    fetchWeatherUseCase.fetchFromOriginal().also {
                        fromOpenMeteoFlag = true
                    }
                }
                weatherData.clear()
                weatherData.addAll(weather)
                isLoading.value = false
                isError.value = false
                showNotificationFromOpenMeteo.value = fromOpenMeteoFlag;
            } catch (e: Exception) {
                isLoading.value = false
                isError.value = true
            }
        }
    }

    fun changeSettings(settings: Settings) {
        viewModelScope.launch {
            saveSettingsUseCase.execute(settings = settings)
            with(this@MainScreenViewModel.settings.value) {

                if (settings.measurementUnits != measurementUnits ||
                    settings.location != location ||
                    settings.language != language
                ) {
                    fetchWeather()
                }
            }
            this@MainScreenViewModel.settings.value = settings
        }
    }

    fun changeSettings(
        location: Location = settings.value.location,
        theme: Theme = settings.value.theme,
        measurementUnitsSet: MeasurementUnitsSet = settings.value.measurementUnits,
        language: Language = settings.value.language
    ) {

        changeSettings(
            Settings(
                location = location, theme = theme,
                measurementUnits = measurementUnitsSet, language = language
            )
        )
    }

    @AppScope
    class Factory @Inject constructor(
        private val fetchWeatherUseCase: FetchWeatherUseCase,
        private val fetchSettingsUseCase: FetchSettingsUseCase,
        private val saveSettingsUseCase: SaveSettingsUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainScreenViewModel(
                fetchWeatherUseCase = fetchWeatherUseCase,
                fetchSettingsUseCase = fetchSettingsUseCase,
                saveSettingsUseCase = saveSettingsUseCase,
            ) as T
        }
    }
}