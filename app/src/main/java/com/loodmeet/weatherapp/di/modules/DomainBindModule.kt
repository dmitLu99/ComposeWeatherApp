package com.loodmeet.weatherapp.di.modules

import com.loodmeet.weatherapp.data.Repository
import com.loodmeet.weatherapp.data.RepositoryImpl
import com.loodmeet.weatherapp.di.AppScope
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
    fun bindWeatherResponseMapper(
        mapper: WeatherResponseMapperImpl
    ): WeatherResponseMapper
}