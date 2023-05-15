package com.loodmeet.weatherapp.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.loodmeet.weatherapp.data.network.WeatherService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.open-meteo.com/v1/"

@Module
class DataModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideDefaultRetrofitClient(gson: Gson): WeatherService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(WeatherService::class.java)
}