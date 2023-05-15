package com.loodmeet.weatherapp.domain.mapper

import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.ui.models.Weather

interface WeatherResponseMapper {

    suspend fun map(response: WeatherResponse, measurementUnitsSet: MeasurementUnitsSet): List<Weather>
}