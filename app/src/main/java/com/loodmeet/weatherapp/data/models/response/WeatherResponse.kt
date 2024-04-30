package com.loodmeet.weatherapp.data.models.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val daily: DailyWeatherResponse
)
