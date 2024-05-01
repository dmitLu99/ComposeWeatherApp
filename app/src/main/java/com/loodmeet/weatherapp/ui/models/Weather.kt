package com.loodmeet.weatherapp.ui.models

import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.domain.mapper.TranslatedWeatherCode

data class Weather(
    val date: String,
    val temperatureMax: String,
    val temperatureMin: String,
    val sunrise: String,
    val sunset: String,
    val apparentTemperatureMin: String,
    val apparentTemperatureMax: String,
    val windSpeed: Double,
    val precipitationSum: Int,
    val hourlyWeather: List<HourlyWeather>,
    val dayLengthIndicator: Float,
    val measurementUnitsSet: MeasurementUnitsSet,
    val translatedDailyWeather: TranslatedWeatherCode,
)