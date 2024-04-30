package com.loodmeet.weatherapp.data.network

import com.loodmeet.weatherapp.data.models.request.WeatherRequest
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val DEFAULT_HOURLY_GET = "temperature_2m,weathercode"
const val DEFAULT_DAILY_GET = "weathercode,temperature_2m_max,temperature_2m_min," +
        "apparent_temperature_max,apparent_temperature_min,sunrise,sunset,precipitation_sum,windspeed_10m_max"

interface WeatherService {

    @GET("forecast") suspend fun executeOriginal(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly", encoded = true) hourly: String = DEFAULT_HOURLY_GET,
        @Query("daily", encoded = true) daily: String = DEFAULT_DAILY_GET,
        @Query("windspeed_unit", encoded = true) windSpeedUnit: String,
        @Query("temperature_unit", encoded = true) temperatureUnit: String,
        @Query("precipitation_unit", encoded = true) precipitationUnit: String,
        @Query("timezone", encoded = true) timezone: String = "auto"
    ): Response<WeatherResponse>

    @POST("forecast") suspend fun execute(
       @Body request: WeatherRequest
    ): Response<WeatherResponse>
}