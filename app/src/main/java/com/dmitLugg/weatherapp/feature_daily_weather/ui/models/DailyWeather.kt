package com.dmitLugg.weatherapp.feature_daily_weather.ui.models

import com.dmitLugg.weatherapp.core.ui.models.HourlyWeather
import com.dmitLugg.weatherapp.core.ui.models.UnitsOfMeasurement

data class DailyWeather(
    val descriptionResId: Int,
    val iconResId: Int,
    val temperatureMax: Int,
    val temperatureMin: Int,
    val sunrise: String,
    val sunset: String,
    val apparentTemperatureMin: Int,
    val apparentTemperatureMax: Int,
    val windSpeed: Double,
    val windDirectionIconResId: Int,
    val precipitationSum: Int,
    val hourlyWeather: List<HourlyWeather>,
    val unitsOfMeasurement: UnitsOfMeasurement
)