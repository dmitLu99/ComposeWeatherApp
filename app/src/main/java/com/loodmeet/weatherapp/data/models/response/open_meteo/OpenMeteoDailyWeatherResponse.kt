package com.loodmeet.weatherapp.data.models.response.open_meteo

import com.google.gson.annotations.SerializedName

data class OpenMeteoDailyWeatherResponse (
    @SerializedName("time")
    val date: List<String>,
    @SerializedName("apparent_temperature_max")
    val apparentTemperatureMax: List<Double>,
    @SerializedName("apparent_temperature_min")
    val apparentTemperatureMin: List<Double>,
    @SerializedName("precipitation_sum")
    val precipitationSum: List<Double>,
    @SerializedName("sunrise")
    val sunrise: List<String>,
    @SerializedName("sunset")
    val sunset: List<String>,
    @SerializedName("temperature_2m_max")
    val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperatureMin: List<Double>,
    @SerializedName("weathercode")
    val weatherCode: List<Int>,
    @SerializedName("windspeed_10m_max")
    val windSpeed: List<Double>
)