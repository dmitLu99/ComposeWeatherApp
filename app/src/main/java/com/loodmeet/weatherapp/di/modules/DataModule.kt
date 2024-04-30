package com.loodmeet.weatherapp.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.loodmeet.weatherapp.data.network.WeatherService
import com.loodmeet.weatherapp.di.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://localhost:8080/api/"

@Module
class DataModule {

    @Provides
    @AppScope
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @AppScope
    fun provideDefaultRetrofitClient(gson: Gson): WeatherService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(WeatherService::class.java)
}