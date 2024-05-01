package com.loodmeet.weatherapp.di.modules

import com.loodmeet.weatherapp.data.Repository
import com.loodmeet.weatherapp.data.RepositoryImpl
import com.loodmeet.weatherapp.data.models.response.WeatherResponse
import com.loodmeet.weatherapp.data.models.response.open_meteo.OpenMeteoWeatherResponse
import com.loodmeet.weatherapp.di.AppScope
import com.loodmeet.weatherapp.domain.mapper.OpenMeteoWeatherResponseMapperImpl
import com.loodmeet.weatherapp.domain.mapper.WeatherResponseMapper
import com.loodmeet.weatherapp.domain.mapper.WeatherResponseMapperImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainBindModule {

    @Binds
    fun bindMainRepository(
        repository: RepositoryImpl
    ): Repository

    @Binds
    @JvmSuppressWildcards
    fun bindWeatherResponseMapper(
        mapper: WeatherResponseMapperImpl
    ): WeatherResponseMapper<WeatherResponse>

    @Binds
    @JvmSuppressWildcards
    fun bindOpenMeteoWeatherResponseMapper(
        mapper: OpenMeteoWeatherResponseMapperImpl
    ): WeatherResponseMapper<OpenMeteoWeatherResponse>
}