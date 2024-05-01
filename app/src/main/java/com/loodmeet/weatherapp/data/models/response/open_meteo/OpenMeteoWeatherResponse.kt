package com.loodmeet.weatherapp.data.models.response.open_meteo

import com.google.gson.annotations.SerializedName
import com.loodmeet.weatherapp.data.models.response.DailyWeatherResponse
import com.loodmeet.weatherapp.data.models.response.HourlyWeatherResponse

data class OpenMeteoWeatherResponse (
    @SerializedName("hourly")
    val hourly: OpenMeteoHourlyWeatherResponse,
    @SerializedName("daily")
    val daily: OpenMeteoDailyWeatherResponse
)