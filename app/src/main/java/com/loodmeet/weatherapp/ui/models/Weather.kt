package com.loodmeet.weatherapp.ui.models

import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet

data class Weather(
    val date: String,
    val descriptionResId: Int,
    val iconResId: Int,
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
    val backgroundId: Int,
    val foregroundColorId: Int
)