package com.loodmeet.weatherapp.domain.mapper

import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.utils.Temperature
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.di.AppScope
import com.loodmeet.weatherapp.ui.models.HourlyWeather
import com.loodmeet.weatherapp.ui.models.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import javax.inject.Inject

@AppScope
class WeatherResponseMapperImpl @Inject constructor() : WeatherResponseMapper {

    private val dailyResponseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val hourlyResponseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    private val dailyFormatter = DateTimeFormatterBuilder()
        .appendPattern("EEE, d MMM")
        .parseDefaulting(ChronoField.YEAR, LocalDate.now().year.toLong())
        .toFormatter()
    private val hourlyFormatter = DateTimeFormatter.ofPattern("HH:mm")

    override suspend fun map(
        response: WeatherResponse,
        measurementUnitsSet: MeasurementUnitsSet,
        location: Location
    ): List<Weather> =
        withContext(Dispatchers.Default) {
            val sunriseTime =
                LocalDateTime.parse(response.daily.sunrise[0], hourlyResponseFormatter)
            val sunsetTime = LocalDateTime.parse(response.daily.sunset[0], hourlyResponseFormatter)

            var offset = 0
            return@withContext List(size = 7) { dailyIndex ->
                if (dailyIndex == 0) offset = LocalDateTime.now().hour

                Weather(
                    date = dailyFormatter.format(
                        LocalDate.parse(
                            response.daily.date[dailyIndex],
                            dailyResponseFormatter
                        )
                    ),
                    descriptionResId = TranslatedWeatherCode.fromWeatherCode(response.daily.weatherCode[dailyIndex]).stringResId,
                    iconResId = TranslatedWeatherCode.fromWeatherCode(response.daily.weatherCode[dailyIndex]).dayImageResId,
                    temperatureMax = Temperature(response.daily.temperatureMax[dailyIndex]).getValueAsString(),
                    temperatureMin = Temperature(response.daily.temperatureMin[dailyIndex]).getValueAsString(),
                    sunrise = hourlyFormatter.format(
                        LocalDateTime.parse(
                            response.daily.sunrise[dailyIndex],
                            hourlyResponseFormatter
                        )
                    ),
                    sunset = hourlyFormatter.format(
                        LocalDateTime.parse(
                            response.daily.sunset[dailyIndex],
                            hourlyResponseFormatter
                        )
                    ),
                    apparentTemperatureMin = Temperature(response.daily.apparentTemperatureMin[dailyIndex]).getValueAsString(),
                    apparentTemperatureMax = Temperature(response.daily.apparentTemperatureMax[dailyIndex]).getValueAsString(),
                    windSpeed = response.daily.windSpeed[dailyIndex],
                    precipitationSum = response.daily.precipitationSum[dailyIndex].toInt(),
                    hourlyWeather = List(size = 24 - offset) { hourlyIndex ->
                        val translatedIndex = dailyIndex * 24 + hourlyIndex + offset
                        val hourlyLocalDateTime = LocalDateTime.parse(
                            response.hourly.time[translatedIndex],
                            hourlyResponseFormatter
                        )
                        val isDay = hourlyLocalDateTime.hour in 4..22
                        val translatedWeather =
                            TranslatedWeatherCode.fromWeatherCode(response.hourly.weatherCode[translatedIndex])
                        HourlyWeather(
                            time = hourlyFormatter.format(hourlyLocalDateTime),
                            descriptionResId = translatedWeather.stringResId,
                            iconResId = if (isDay) translatedWeather.dayImageResId else translatedWeather.nightImageResId,
                            temperature = Temperature(response.hourly.temperature2m[translatedIndex]).getValueAsString()
                        )
                    },
                    measurementUnitsSet = measurementUnitsSet,
                    dayLengthIndicator = (sunsetTime.hour + sunsetTime.minute / 60 - sunriseTime.hour - sunriseTime.minute / 60) / location.maxDayLengthInHours.toFloat()
                )
            }
        }
}