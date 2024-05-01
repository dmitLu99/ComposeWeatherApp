package com.loodmeet.weatherapp.data.models.response.open_meteo

import com.google.gson.annotations.SerializedName

data class OpenMeteoHourlyWeatherResponse(
    @SerializedName("weathercode")
    val weatherCode: List<Int>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("time")
    val time: List<String>
)