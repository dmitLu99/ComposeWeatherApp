package com.loodmeet.weatherapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.loodmeet.weatherapp.ui.navigation.AppNavHost
import com.loodmeet.weatherapp.ui.theme.ComposeWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeWeatherAppTheme {
                AppNavHost()
            }
        }
    }
}