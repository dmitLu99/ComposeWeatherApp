package com.loodmeet.weatherapp.data

import android.util.Log
import com.loodmeet.weatherapp.core.exceptions.RequestExecuteException
import com.loodmeet.weatherapp.core.exceptions.ResponseIsNotSuccessfulException
import com.loodmeet.weatherapp.core.models.Location
import com.loodmeet.weatherapp.core.models.MeasurementUnitsSet
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.data.network.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: WeatherService
) : Repository {

    override suspend fun fetchLocation(): Location = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun saveLocation() = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMeasurementUnitsSet(): MeasurementUnitsSet = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun saveMeasurementUnitsSet() = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchWeather(
        location: Location,
        measurementUnitsSet: MeasurementUnitsSet
    ): WeatherResponse = withContext(Dispatchers.IO) {
        return@withContext try {
            service.execute(
                latitude = location.latitude, longitude = location.longitude,
                windSpeedUnit = measurementUnitsSet.windSpeedUnit.requestName,
                temperatureUnit = measurementUnitsSet.temperatureUnit.requestName,
                precipitationUnit = measurementUnitsSet.precipitationUnit.requestName
            ).also { retrofitResponse ->
                if (!retrofitResponse.isSuccessful) throw ResponseIsNotSuccessfulException(
                    message = retrofitResponse.message()
                )
            }.body()!!.also {
                Log.d(Config.LOG.NETWORK_TAG, "Request error: $it")
            }
        } catch (e: Exception) {
            throw RequestExecuteException(cause = e, message = "Request error")
        }
    }
}