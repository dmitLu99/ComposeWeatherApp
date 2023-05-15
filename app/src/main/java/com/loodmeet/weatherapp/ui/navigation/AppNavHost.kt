package com.loodmeet.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loodmeet.weatherapp.core.utils.Config.NAVIGATION.MAIN_SCREEN
import com.loodmeet.weatherapp.ui.main_screen.MainScreen
import com.loodmeet.weatherapp.ui.veiw_models.MainScreenViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MAIN_SCREEN,
    mainScreenViewModelFactory: MainScreenViewModel.Factory
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(MAIN_SCREEN) {
            MainScreen(viewModel = viewModel(factory = mainScreenViewModelFactory))
        }
    }
}