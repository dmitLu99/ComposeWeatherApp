package com.loodmeet.weatherapp.ui.veiw_models

import androidx.lifecycle.ViewModel
import com.loodmeet.weatherapp.di.DaggerMainActivityComponent

class MainActivityComponentViewModel : ViewModel() {


    private val component = DaggerMainActivityComponent.create()
    fun getDaggerComponent() = component!!
}