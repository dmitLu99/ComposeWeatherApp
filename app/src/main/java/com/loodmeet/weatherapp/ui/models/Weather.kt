package com.loodmeet.weatherapp.ui.models

import com.loodmeet.weatherapp.core.models.UnitOfMeasurement

data class Weather(
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
    val windSpeedUnit: UnitOfMeasurement.WindSpeedUnit,
    val precipitationUnit: UnitOfMeasurement.PrecipitationUnit
)