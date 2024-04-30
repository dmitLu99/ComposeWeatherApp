package com.loodmeet.weatherapp.data.models.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class HourlyWeatherResponse(
    val weatherCode: Int,
    val temperature: Double,
    val time: String,
    val isDay: Boolean
)