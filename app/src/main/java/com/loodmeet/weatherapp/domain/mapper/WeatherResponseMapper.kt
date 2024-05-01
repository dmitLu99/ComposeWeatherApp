package com.loodmeet.weatherapp.domain.mapper

import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.ui.models.Weather

interface WeatherResponseMapper<Response> {

    suspend fun map(
        response: Response,
        measurementUnitsSet: MeasurementUnitsSet,
        location: Location
    ): List<Weather>
}