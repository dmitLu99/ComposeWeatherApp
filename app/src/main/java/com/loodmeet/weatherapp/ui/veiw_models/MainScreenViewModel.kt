package com.loodmeet.weatherapp.ui.veiw_models

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.models.UnitOfMeasurement
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.ui.models.HourlyWeather
import com.loodmeet.weatherapp.ui.models.Weather

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

    var weatherData = mutableStateOf(weather)

    fun fetchWeather() {
//        weatherData.value = weather
    }

    fun changeWindSpeedUnit(unit: UnitOfMeasurement.WindSpeedUnit) {
        weatherData.value = weather.copy(windSpeedUnit = unit)
    }
}