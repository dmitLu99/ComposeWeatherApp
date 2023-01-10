package com.dmitLugg.weatherapp.feature_main_screen.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.HourlyWeather

@Composable
fun HourlyWeather(hourlyWeatherList: List<HourlyWeather>, onNavigateToDailyWeather: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxHeight()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text(text = "Today", fontSize = 20.sp)
                }
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {

                    Row(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = rememberRipple(bounded = false),
                            onClick = onNavigateToDailyWeather
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Week", fontSize = 20.sp)
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }


                }
            }
            LazyRow {
                items(hourlyWeatherList) { hourlyWeather ->
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = hourlyWeather.time)
                        Spacer(modifier = Modifier.height(10.dp))
                        Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = hourlyWeather.temperature)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}