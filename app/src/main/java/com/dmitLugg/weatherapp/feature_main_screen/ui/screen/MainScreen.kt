package com.dmitLugg.weatherapp.feature_main_screen.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmitLugg.weatherapp.R
import com.dmitLugg.weatherapp.app.ui.theme.ComposeWeatherAppTheme
import com.dmitLugg.weatherapp.feature_main_screen.ui.composable.AppBar
import com.dmitLugg.weatherapp.feature_main_screen.ui.composable.CurrentWeather
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.CurrentWeather
import com.dmitLugg.weatherapp.feature_main_screen.ui.composable.HourlyWeather
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.HourlyWeather
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.Location
import com.dmitLugg.weatherapp.feature_main_screen.ui.shapes.CustomShape
import java.time.LocalDate
import java.time.LocalTime

@[Preview(showBackground = true) Composable]
fun MainScreen() {

    var offset by remember { mutableStateOf(0f) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(
                state = rememberScrollableState {
                    offset += it
                    it
                },
                orientation = Orientation.Vertical
            )
    ) {


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f),
            shadowElevation = 6.dp,
            shape = CustomShape()
        ) {
            Image(
                painter = painterResource(id = R.drawable.main_bg7),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )


            ComposeWeatherAppTheme(darkTheme = true, dynamicColor = false) {
                Column {

                    AppBar(Location("Russia", "Volgograd"))

                    CurrentWeather(
                        modifier = Modifier.weight(1f),
                        currentWeather = CurrentWeather(
                            LocalDate.now().toString(),
                            temperature = "19",
                            description = "cloudy"
                        )
                    )
                    Spacer(modifier = Modifier.weight(0.8f))
                }
            }

        }



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f, fill = false), contentAlignment = Alignment.Center
        ) {
            HourlyWeather(hourlyWeatherList = List(size = 23) { index ->
                HourlyWeather(time = LocalTime.of(index, 0).toString(), "", index.toString())
            })
        }

    }
}