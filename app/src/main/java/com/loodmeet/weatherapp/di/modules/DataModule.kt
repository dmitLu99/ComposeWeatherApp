package com.loodmeet.weatherapp.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.loodmeet.weatherapp.data.network.OpenMeteoWeatherService
import com.loodmeet.weatherapp.data.network.WeatherService
import com.loodmeet.weatherapp.di.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://10.0.2.2:8080/api/"
private const val OPEN_METEO_BASE_URL = "https://api.open-meteo.com/v1/"

@Module
class DataModule {

    @Provides
    @AppScope
    fun provideGson(): Gson = GsonBuilder().create()
    @Provides
    @AppScope
    fun okhttp(): OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build();

    @Provides
    @AppScope
    fun provideDefaultRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): WeatherService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(WeatherService::class.java)

    @Provides
    @AppScope
    fun provideOpenMetoRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): OpenMeteoWeatherService =
        Retrofit.Builder()
            .baseUrl(OPEN_METEO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(OpenMeteoWeatherService::class.java)
}