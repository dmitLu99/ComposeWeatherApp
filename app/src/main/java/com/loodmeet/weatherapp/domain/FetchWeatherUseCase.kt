package com.loodmeet.weatherapp.domain

import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.models.MeasurementUnit
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.ui.models.HourlyWeather
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor() {

    private val hourlyWeather = List(size = 24) {
        HourlyWeather(
            time = "10:00",
            description = "cloudy",
            iconId = R.drawable.ic_sky_32,
            temperature = "+10"
        )
    }

    suspend fun fetchWeather(measurementUnitsSet: MeasurementUnitsSet): List<Weather> =
        withContext(Dispatchers.IO) {
            delay(0)
            return@withContext List(size = 7) { index ->
                Weather(
                    date = when (index) {
                        0 -> "Today"
                        1 -> "Tomorrow"
                        else -> "Mon, 4 Sep"
                    },
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
                    measurementUnitsSet = measurementUnitsSet
                )
            }
        }
}