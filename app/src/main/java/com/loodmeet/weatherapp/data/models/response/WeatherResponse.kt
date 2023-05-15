package com.loodmeet.weatherapp.data.models.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("hourly")
    val hourly: HourlyWeatherResponse,
    @SerializedName("daily")
    val daily: DailyWeatherResponse
)
