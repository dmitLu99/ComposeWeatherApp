package com.loodmeet.weatherapp.domain.use_case

import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.data.Repository
import com.loodmeet.weatherapp.domain.mapper.WeatherResponseMapper
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(
    private val repository: Repository,
    private val responseMapper: WeatherResponseMapper,
    private val fetchLocationUseCase: FetchLocationUseCase
) {

    suspend fun fetchWeather(measurementUnitsSet: MeasurementUnitsSet): List<Weather> =
        withContext(Dispatchers.Default) {
            return@withContext responseMapper.map(repository.fetchWeather(
                location = fetchLocationUseCase.execute(),
                measurementUnitsSet = measurementUnitsSet
            ), measurementUnitsSet)
        }
}