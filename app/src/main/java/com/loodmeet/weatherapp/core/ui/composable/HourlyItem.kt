package com.loodmeet.weatherapp.core.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.loodmeet.weatherapp.core.ui.models.HourlyWeather

@Composable
fun HourlyItem(
    modifier: Modifier = Modifier,
    hourlyWeather: HourlyWeather
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .height(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = hourlyWeather.time)
        Icon(painterResource(id = hourlyWeather.iconResId), contentDescription = null, modifier = Modifier.size(36.dp))
        Text(text = hourlyWeather.temperature)
    }
}