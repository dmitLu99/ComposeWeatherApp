package com.loodmeet.weatherapp.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import com.loodmeet.weatherapp.core.exceptions.RequestExecuteException
import com.loodmeet.weatherapp.core.exceptions.ResponseIsNotSuccessfulException
import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnit
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
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
const val PREFS_NAME = "com.loodmeet.weatherapp"

@AppScope
class RepositoryImpl @Inject constructor(
    private val service: WeatherService,
    application: Application
) : Repository {

    private var location: Location = Location.Moscow
    private var measurementUnitsSet: MeasurementUnitsSet = MeasurementUnitsSet()
    private var prefs =
        application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun fetchLocation(): Location = withContext(Dispatchers.IO) {
        location =
            Location.getList().stream().filter { it.cityResId == prefs.getInt(LOCATION_KEY, -1) }
                .findFirst().orElse(location)
        return@withContext location
    }

    override suspend fun saveLocation(location: Location) = withContext(Dispatchers.IO) {
        prefs.edit().putInt(LOCATION_KEY, location.cityResId).apply()
        this@RepositoryImpl.location = location
    }

    override suspend fun fetchMeasurementUnitsSet(): MeasurementUnitsSet =
        withContext(Dispatchers.IO) {
            measurementUnitsSet = MeasurementUnitsSet(
                temperatureUnit = MeasurementUnit.TemperatureUnit.getList().stream().filter {
                    it.unitResId == prefs.getInt(
                        TEMPERATURE_KEY, -1
                    )
                }.findFirst().orElse(measurementUnitsSet.temperatureUnit),
                windSpeedUnit = MeasurementUnit.WindUnitSpeedUnit.getList().stream().filter {
                    it.unitResId == prefs.getInt(
                        WIND_SPEED_KEY, -1
                    )
                }.findFirst().orElse(measurementUnitsSet.windSpeedUnit),
                precipitationUnit = MeasurementUnit.PrecipitationUnit.getList().stream().filter {
                    it.unitResId == prefs.getInt(
                        PRECIPITATION_KEY, -1
                    )
                }.findFirst().orElse(measurementUnitsSet.precipitationUnit)
            )
            return@withContext measurementUnitsSet
        }

    override suspend fun saveMeasurementUnitsSet(measurementUnitsSet: MeasurementUnitsSet) =
        withContext(Dispatchers.IO) {
            prefs.edit()
                .putInt(TEMPERATURE_KEY, measurementUnitsSet.temperatureUnit.unitResId)
                .putInt(WIND_SPEED_KEY, measurementUnitsSet.windSpeedUnit.unitResId)
                .putInt(PRECIPITATION_KEY, measurementUnitsSet.precipitationUnit.unitResId)
                .apply()
            this@RepositoryImpl.measurementUnitsSet = measurementUnitsSet
        }

    override suspend fun fetchWeather(): WeatherResponse = withContext(Dispatchers.IO) {
        return@withContext try {
            service.execute(
                location = location.javaClass.name.uppercase(Locale.ROOT),
                windSpeedUnit = measurementUnitsSet.windSpeedUnit.requestName,
                temperatureUnit = measurementUnitsSet.temperatureUnit.requestName,
                precipitationUnit = measurementUnitsSet.precipitationUnit.requestName
            ).also { retrofitResponse ->
                if (!retrofitResponse.isSuccessful) {
                    Log.d(Config.LOG.NETWORK_TAG, retrofitResponse.errorBody().toString())

                    throw ResponseIsNotSuccessfulException(
                        message = retrofitResponse.message()
                    )
                }
            }.body()!!
        } catch (e: Exception) {
            throw RequestExecuteException(cause = e, message = "Request error")
        }
    }
}