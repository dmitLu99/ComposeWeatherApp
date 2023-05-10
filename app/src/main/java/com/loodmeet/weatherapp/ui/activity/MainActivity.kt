package com.loodmeet.weatherapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.loodmeet.weatherapp.ui.navigation.AppNavHost
import com.loodmeet.weatherapp.ui.theme.ComposeWeatherAppTheme
import com.loodmeet.weatherapp.ui.veiw_models.MainScreenViewModel

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