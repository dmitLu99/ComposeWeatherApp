package com.dmitLugg.weatherapp.feature_main_screen.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.CurrentWeather

@Composable
fun CurrentWeather(modifier: Modifier = Modifier, currentWeather: CurrentWeather) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentWeather.date,
                fontSize = 22.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = currentWeather.temperature, fontSize = 110.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = currentWeather.description, fontSize = 22.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
        }
    }


}