package com.loodmeet.weatherapp.data

import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.models.Settings
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.data.models.response.open_meteo.OpenMeteoWeatherResponse

interface Repository {

    fun fetchSettings(): Settings
    suspend fun saveSettings(settings: Settings)
    suspend fun fetchWeather(): WeatherResponse
    suspend fun fetchWeatherFromOpenMeteo(): OpenMeteoWeatherResponse
}