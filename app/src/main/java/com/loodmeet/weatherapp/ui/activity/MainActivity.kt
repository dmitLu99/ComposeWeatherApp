package com.loodmeet.weatherapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.loodmeet.weatherapp.app.appComponent
import com.loodmeet.weatherapp.ui.navigation.AppNavHost
import com.loodmeet.weatherapp.ui.theme.ComposeWeatherAppTheme
import com.loodmeet.weatherapp.ui.veiw_models.MainScreenViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var mainScreenViewModelFactory: MainScreenViewModel.Factory

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            ComposeWeatherAppTheme {
                AppNavHost(mainScreenViewModelFactory = mainScreenViewModelFactory)
            }
        }
    }
}
