package com.loodmeet.weatherapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.loodmeet.weatherapp.core.utils.Config
import com.loodmeet.weatherapp.di.MainActivityComponent
import com.loodmeet.weatherapp.ui.navigation.AppNavHost
import com.loodmeet.weatherapp.ui.theme.ComposeWeatherAppTheme
import com.loodmeet.weatherapp.ui.veiw_models.MainActivityComponentViewModel
import com.loodmeet.weatherapp.ui.veiw_models.MainScreenViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private lateinit var component: MainActivityComponent
    private lateinit var componentViewModel: MainActivityComponentViewModel

    @Inject
    internal lateinit var mainScreenViewModelFactory: MainScreenViewModel.Factory

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Config.LOG.logDebug("1")
            componentViewModel = viewModel()
            component = componentViewModel.getDaggerComponent()
            component.inject(this)
            Config.LOG.logDebug("2")

            ComposeWeatherAppTheme {
                Config.LOG.logDebug("3")

                AppNavHost(mainScreenViewModelFactory = mainScreenViewModelFactory)
            }
        }
    }
}
