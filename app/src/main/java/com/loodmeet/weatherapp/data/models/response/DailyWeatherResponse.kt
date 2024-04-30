package com.loodmeet.weatherapp.data.models.response

data class DailyWeatherResponse(
    val date: String,
    val temperatureMax: Double,
    val temperatureMin: Double,
    val sunrise: String,
    val sunset: String,
    val apparentTemperatureMin: Double,
    val apparentTemperatureMax: Double,
    val windSpeed: Double,
    val precipitationSum: Int,
    val hourlyWeather: List<HourlyWeatherResponse>,
    val dayLengthIndicator: Float
)