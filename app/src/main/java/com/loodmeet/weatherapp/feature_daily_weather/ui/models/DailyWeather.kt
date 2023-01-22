package com.loodmeet.weatherapp.feature_daily_weather.ui.models

import com.loodmeet.weatherapp.core.ui.models.HourlyWeather
import com.loodmeet.weatherapp.core.ui.models.UnitsOfMeasurementResIds

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
    val unitsOfMeasurementResIds: UnitsOfMeasurementResIds
)