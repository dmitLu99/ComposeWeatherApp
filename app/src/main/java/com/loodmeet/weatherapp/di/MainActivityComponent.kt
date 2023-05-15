package com.loodmeet.weatherapp.di

import com.loodmeet.weatherapp.di.modules.DataModule
import com.loodmeet.weatherapp.di.modules.DomainBindModule
import com.loodmeet.weatherapp.di.modules.DomainModule
import com.loodmeet.weatherapp.ui.activity.MainActivity
import dagger.Component

@Component(modules = arrayOf(DomainModule::class, DataModule::class, DomainBindModule::class))
interface MainActivityComponent {

    fun inject(activity: MainActivity)
}