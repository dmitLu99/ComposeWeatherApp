package com.loodmeet.weatherapp.data.models.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class HourlyWeatherResponse(
    @SerializedName("weathercode")
    val weatherCode: List<Int>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("time")
    val time: List<String>
)