package com.loodmeet.weatherapp.domain

import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.models.UnitOfMeasurement
import com.loodmeet.weatherapp.ui.models.HourlyWeather
import com.loodmeet.weatherapp.ui.models.Weather
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

    suspend fun fetchWeather(): Weather {
        return weather;
    }
}