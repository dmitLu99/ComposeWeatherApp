package com.loodmeet.weatherapp.ui.veiw_models

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.models.UnitOfMeasurement
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.ui.models.HourlyWeather
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    private val hourlyWeather = List(size = 24) {
        HourlyWeather(
            time = "10:00",
            description = "cloudy",
            iconId = R.drawable.ic_sky_32,
            temperature = "+10"
        )
    }

    private val weather = Weather(
        descriptionResId = R.string.cloudy,
        iconResId = R.drawable.ic_sky_32,
        temperatureMax = 10,
        temperatureMin = 10,
        sunrise = "10:10",
        sunset = "10:10",
        apparentTemperatureMax = 10,
        apparentTemperatureMin = 10,
        windSpeed = 10.1,
        windDirectionIconResId = R.drawable.ic_person_outlined,
        precipitationSum = 10,
        hourlyWeather = hourlyWeather,
        windSpeedUnit = UnitOfMeasurement.WindSpeedUnit.MetresPerSecond,
        precipitationUnit = UnitOfMeasurement.PrecipitationUnit.Millimeter
    )

    var weatherData = mutableStateOf<Weather?>(null)
    var isInit = mutableStateOf(true)
    fun fetchWeather() {
        viewModelScope.launch {
            delay(5000)
            isInit.value = false
            weatherData.value = weather
            changeWindSpeedUnit(unit = UnitOfMeasurement.WindSpeedUnit.MetresPerSecond)
        }

    }

    fun changeWindSpeedUnit(unit: UnitOfMeasurement.WindSpeedUnit) {
        weatherData.value = weather.copy(windSpeedUnit = unit)
    }
}