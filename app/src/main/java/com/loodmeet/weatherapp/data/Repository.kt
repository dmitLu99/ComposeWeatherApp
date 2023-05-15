package com.loodmeet.weatherapp.data

import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.data.models.response.WeatherResponse

interface Repository {

    suspend fun fetchLocation(): Location
    suspend fun saveLocation()
    suspend fun fetchMeasurementUnitsSet(): MeasurementUnitsSet
    suspend fun saveMeasurementUnitsSet()
    suspend fun fetchWeather(
        location: Location,
        measurementUnitsSet: MeasurementUnitsSet
    ): WeatherResponse
}