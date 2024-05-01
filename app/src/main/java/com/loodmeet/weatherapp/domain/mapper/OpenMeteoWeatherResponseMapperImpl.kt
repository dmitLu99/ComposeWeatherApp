package com.loodmeet.weatherapp.domain.mapper

import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.models.Settings
import com.loodmeet.weatherapp.core.utils.Temperature
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.data.models.response.open_meteo.OpenMeteoWeatherResponse
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
import java.util.Locale
import javax.inject.Inject

@AppScope
class OpenMeteoWeatherResponseMapperImpl @Inject constructor() : WeatherResponseMapper<OpenMeteoWeatherResponse> {

    private val dailyResponseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val hourlyResponseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    override suspend fun map(
        response: OpenMeteoWeatherResponse,
        settings: Settings
    ): List<Weather> =
        withContext(Dispatchers.Default) {
            val locale = Locale(settings.language.getTag())
            val dailyFormatter = DateTimeFormatterBuilder()
                .appendPattern("EEE, d MMM")
                .parseDefaulting(ChronoField.YEAR, LocalDate.now().year.toLong())
                .toFormatter(locale)
            val hourlyFormatter = DateTimeFormatter.ofPattern("HH:mm", locale)

            val sunriseTime =
                LocalDateTime.parse(response.daily.sunrise[0], hourlyResponseFormatter)
            val sunsetTime = LocalDateTime.parse(response.daily.sunset[0], hourlyResponseFormatter)

            return@withContext List(size = 7) { dailyIndex ->
                val translatedDailyWeather = TranslatedWeatherCode.fromWeatherCode(response.daily.weatherCode[dailyIndex])
                Weather(
                    date = dailyFormatter.format(
                        LocalDate.parse(
                            response.daily.date[dailyIndex],
                            dailyResponseFormatter
                        )
                    ),
                    translatedDailyWeather = translatedDailyWeather,
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
                    hourlyWeather = List(size = 8) { hourlyIndex ->
                        val translatedIndex = dailyIndex * 24 + hourlyIndex * 3
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
                    measurementUnitsSet = settings.measurementUnits,
                    dayLengthIndicator = (sunsetTime.hour + sunsetTime.minute / 60 - sunriseTime.hour - sunriseTime.minute / 60) / settings.location.maxDayLengthInHours.toFloat(),
                )
            }
        }
}