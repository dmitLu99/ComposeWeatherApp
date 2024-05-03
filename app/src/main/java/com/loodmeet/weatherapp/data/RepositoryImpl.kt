package com.loodmeet.weatherapp.data

import android.app.Application
import android.content.Context
import android.util.Log
import com.loodmeet.weatherapp.core.exceptions.RequestExecuteException
import com.loodmeet.weatherapp.core.exceptions.ResponseIsNotSuccessfulException
import com.loodmeet.weatherapp.core.models.Language
import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnit
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.models.Settings
import com.loodmeet.weatherapp.core.models.Theme
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.data.models.request.WeatherRequest
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.data.models.response.open_meteo.OpenMeteoWeatherResponse
import com.loodmeet.weatherapp.data.network.OpenMeteoWeatherService
import com.loodmeet.weatherapp.data.network.WeatherService
import com.loodmeet.weatherapp.di.AppScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

const val TEMPERATURE_KEY = "temperature"
const val WIND_SPEED_KEY = "wind_speed"
const val PRECIPITATION_KEY = "precipitation"
const val LOCATION_KEY = "location"
const val THEME_KEY = "theme"
const val LANGUAGE_KEY = "language"
const val PREFS_NAME = "com.loodmeet.weatherapp"

@AppScope
class RepositoryImpl @Inject constructor(
    private val service: WeatherService,
    private val openMeteoService: OpenMeteoWeatherService,
    application: Application
) : Repository {

    private var settings: Settings = Settings(
        measurementUnits = MeasurementUnitsSet(),
        location = Location.Moscow,
        language = Language.English,
        theme = Theme.System
    )

    private var prefs =
        application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    override suspend fun fetchWeather(): WeatherResponse = withContext(Dispatchers.IO) {
        return@withContext try {
            service.execute(
                location = settings.location.javaClass.simpleName.uppercase(Locale.ROOT),
                windSpeedUnit = settings.measurementUnits.windSpeedUnit.requestName.uppercase(),
                precipitationUnit = settings.measurementUnits.precipitationUnit.requestName.uppercase(),
                temperatureUnit = settings.measurementUnits.temperatureUnit.requestName.uppercase()
            ).also { retrofitResponse ->
                if (!retrofitResponse.isSuccessful) {
                    retrofitResponse.errorBody()?.let { Log.d(Config.LOG.NETWORK_TAG, it.string()) }

                    throw ResponseIsNotSuccessfulException(
                        message = retrofitResponse.message()
                    )
                }
            }.body()!!
        } catch (e: Exception) {
            throw RequestExecuteException(cause = e, message = "Request error")
        }
    }

    override suspend fun fetchWeatherFromOpenMeteo(): OpenMeteoWeatherResponse =
        withContext(Dispatchers.IO) {
            return@withContext try {
                openMeteoService.execute(
                    latitude = settings.location.latitude, longitude = settings.location.longitude,
                    windSpeedUnit = settings.measurementUnits.windSpeedUnit.requestName,
                    temperatureUnit = settings.measurementUnits.temperatureUnit.requestName,
                    precipitationUnit = settings.measurementUnits.precipitationUnit.requestName
                ).also { retrofitResponse ->
                    if (!retrofitResponse.isSuccessful) {
                        retrofitResponse.errorBody()
                            ?.let { Log.d(Config.LOG.NETWORK_TAG, it.string()) }

                        throw ResponseIsNotSuccessfulException(
                            message = retrofitResponse.message()
                        )
                    }
                }.body()!!
            } catch (e: Exception) {
                throw RequestExecuteException(cause = e, message = "Request error")
            }
        }

    override fun fetchSettings(): Settings {

        val locationId = prefs.getInt(LOCATION_KEY, -1)
        val themeId = prefs.getInt(THEME_KEY, -1)
        val languageId = prefs.getInt(LANGUAGE_KEY, -1)

        val tempId = prefs.getInt(TEMPERATURE_KEY, -1)
        val windSpeedId = prefs.getInt(WIND_SPEED_KEY, -1)
        val precipitationId = prefs.getInt(PRECIPITATION_KEY, -1)

        val location = Location.getList().stream()
            .filter { it.cityResId == locationId }
            .findFirst().orElse(settings.location)
        val theme = Theme.getList().stream()
            .filter { it.nameResId == themeId }
            .findFirst().orElse(settings.theme)
        val language = Language.getList().stream()
            .filter { it.nameResId == languageId }
            .findFirst().orElse(settings.language)

        val units = MeasurementUnitsSet(
            temperatureUnit = MeasurementUnit.TemperatureUnit.getList().stream().filter {
                it.unitResId == tempId
            }.findFirst().orElse(settings.measurementUnits.temperatureUnit),
            windSpeedUnit = MeasurementUnit.WindUnitSpeedUnit.getList().stream().filter {
                it.unitResId == windSpeedId
            }.findFirst().orElse(settings.measurementUnits.windSpeedUnit),
            precipitationUnit = MeasurementUnit.PrecipitationUnit.getList().stream().filter {
                it.unitResId == precipitationId
            }.findFirst().orElse(settings.measurementUnits.precipitationUnit)
        )

        settings.location = location
        settings.theme = theme
        settings.language = language
        settings.measurementUnits = units

        return settings;
    }

    override suspend fun saveSettings(settings: Settings) = withContext(Dispatchers.IO) {
        prefs.edit().putInt(LOCATION_KEY, settings.location.cityResId).apply()
        prefs.edit().putInt(THEME_KEY, settings.theme.nameResId).apply()
        prefs.edit().putInt(LANGUAGE_KEY, settings.language.nameResId).apply()

        prefs.edit()
            .putInt(TEMPERATURE_KEY, settings.measurementUnits.temperatureUnit.unitResId)
            .putInt(WIND_SPEED_KEY, settings.measurementUnits.windSpeedUnit.unitResId)
            .putInt(PRECIPITATION_KEY, settings.measurementUnits.precipitationUnit.unitResId)
            .apply()

        this@RepositoryImpl.settings = settings
    }
}