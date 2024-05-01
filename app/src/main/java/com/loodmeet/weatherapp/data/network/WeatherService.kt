package com.loodmeet.weatherapp.data.network

import com.loodmeet.weatherapp.data.models.request.WeatherRequest
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.data.models.response.open_meteo.OpenMeteoWeatherResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface WeatherService {

    @POST("forecast") suspend fun execute(
       @Body request: WeatherRequest
    ): Response<WeatherResponse>
}