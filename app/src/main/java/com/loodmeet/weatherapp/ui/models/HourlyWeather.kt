package com.loodmeet.weatherapp.ui.models

data class HourlyWeather(
    val time: String,
    val description: String,
    val iconId: Int,
    val temperature: String
)