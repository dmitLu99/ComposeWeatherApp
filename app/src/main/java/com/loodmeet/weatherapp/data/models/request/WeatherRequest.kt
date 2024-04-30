package com.loodmeet.weatherapp.data.models.request

data class WeatherRequest(
    val location: String,
    val windSpeedUnit: String,
    val temperatureUnit: String,
    val precipitationUnit: String,
    val timezone: String = "auto"
)