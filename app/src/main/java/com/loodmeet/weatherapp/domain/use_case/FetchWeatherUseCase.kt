package com.loodmeet.weatherapp.domain.use_case

import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.data.Repository
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.data.models.response.open_meteo.OpenMeteoWeatherResponse
import com.loodmeet.weatherapp.di.AppScope
import com.loodmeet.weatherapp.domain.mapper.TranslatedWeatherCode
import com.loodmeet.weatherapp.domain.mapper.WeatherResponseMapper
import com.loodmeet.weatherapp.ui.models.HourlyWeather
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AppScope
class FetchWeatherUseCase @Inject constructor(
    private val repository: Repository,
    private val responseMapper: WeatherResponseMapper<WeatherResponse>,
    private val openMeteoResponseMapper: WeatherResponseMapper<OpenMeteoWeatherResponse>,
) {

    suspend fun execute(): List<Weather> = withContext(Dispatchers.Default) {

        return@withContext responseMapper.map(
            repository.fetchWeather(),
            repository.fetchSettings()
        )
    }

    suspend fun fetchFromOriginal(): List<Weather> = withContext(Dispatchers.Default) {

        return@withContext openMeteoResponseMapper.map(
            repository.fetchWeatherFromOpenMeteo(),
            repository.fetchSettings()
        )
    }

    fun fetchTestData() = List(size = 7) {
        Weather(
            date = "1",
            translatedDailyWeather = TranslatedWeatherCode.CLEAR_SKY,
            temperatureMax = "1",
            temperatureMin = "1",
            sunrise = "1",
            sunset = "1",
            apparentTemperatureMin = "1",
            apparentTemperatureMax = "1",
            windSpeed = 1.1,
            precipitationSum = 1,
            hourlyWeather = List(size = 24) { hourlyIndex ->
                HourlyWeather(
                    time = "1",
                    descriptionResId = if (hourlyIndex == 1) R.string.partly_cloudy else R.string.inch,
                    iconResId = R.drawable.ic_sky_32,
                    temperature = "1"
                )
            },
            measurementUnitsSet = MeasurementUnitsSet(),
            dayLengthIndicator = 0.7f
        )
    }

}