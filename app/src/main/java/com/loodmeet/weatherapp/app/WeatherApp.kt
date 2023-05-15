package com.loodmeet.weatherapp.app

import android.app.Application
import android.content.Context
import com.loodmeet.weatherapp.di.DaggerMainActivityComponent
import com.loodmeet.weatherapp.di.MainActivityComponent

class WeatherApp : Application() {

    lateinit var appComponent: MainActivityComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerMainActivityComponent.builder().application(application = this).build()
    }
}

internal val Context.appComponent: MainActivityComponent
    get() = when (this) {
        is WeatherApp -> appComponent
        else -> this.applicationContext.appComponent
    }