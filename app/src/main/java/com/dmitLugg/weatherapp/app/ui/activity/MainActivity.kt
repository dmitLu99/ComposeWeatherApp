package com.dmitLugg.weatherapp.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dmitLugg.weatherapp.app.ui.composable.AppNavHost
import com.dmitLugg.weatherapp.app.ui.theme.ComposeWeatherAppTheme

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