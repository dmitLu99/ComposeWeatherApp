package com.loodmeet.weatherapp.ui.models

data class HourlyWeather(
    val time: String,
    val descriptionResId: Int,
    val iconResId: Int,
    val temperature: String
)