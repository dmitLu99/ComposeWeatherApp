package com.loodmeet.weatherapp.data

import android.util.Log
import com.loodmeet.weatherapp.core.exceptions.RequestExecuteException
import com.loodmeet.weatherapp.core.exceptions.ResponseIsNotSuccessfulException
import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.data.network.WeatherService
import com.loodmeet.weatherapp.di.AppScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AppScope
class RepositoryImpl @Inject constructor(
    private val service: WeatherService
) : Repository {

    init {
        Log.d(Config.LOG.NETWORK_TAG, "create")

    }
    private var location: Location = Location.Moscow
    private var measurementUnitsSet: MeasurementUnitsSet = MeasurementUnitsSet()

    override suspend fun fetchLocation(): Location = withContext(Dispatchers.IO) {
        return@withContext location
    }

    override suspend fun saveLocation(location: Location) = withContext(Dispatchers.IO) {
        Log.d(Config.LOG.NETWORK_TAG, "1")

        this@RepositoryImpl.location = location
    }

    override suspend fun fetchMeasurementUnitsSet(): MeasurementUnitsSet =
        withContext(Dispatchers.IO) {
            return@withContext measurementUnitsSet
        }

    override suspend fun saveMeasurementUnitsSet(measurementUnitsSet: MeasurementUnitsSet) =
        withContext(Dispatchers.IO) {
            this@RepositoryImpl.measurementUnitsSet = measurementUnitsSet
        }

    override suspend fun fetchWeather(): WeatherResponse = withContext(Dispatchers.IO) {
        Log.d(Config.LOG.NETWORK_TAG, "2")
        return@withContext try {
            service.execute(
                latitude = location.latitude, longitude = location.longitude,
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