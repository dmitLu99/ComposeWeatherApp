package com.loodmeet.weatherapp.app.ui.composable

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loodmeet.weatherapp.core.utils.Config.NAVIGATION.DAILY_WEATHER_SCREEN
import com.loodmeet.weatherapp.core.utils.Config.NAVIGATION.MAIN_SCREEN
import com.loodmeet.weatherapp.feature_daily_weather.ui.screen.DailyWeatherScreen
import com.loodmeet.weatherapp.feature_main_screen.ui.screen.MainScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MAIN_SCREEN
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(MAIN_SCREEN) {
//            MainScreen(onNavigateToDailyWeather = {
//                navController.navigate(
//                    DAILY_WEATHER_SCREEN
//                )
//            })
            com.loodmeet.weatherapp.ui.main_screen.MainScreen()
        }
//        composable(DAILY_WEATHER_SCREEN) {
//            DailyWeatherScreen(
//                onNavigateBack = {
//                    navController.popBackStack()
//                }
//            )
//        }
    }
}