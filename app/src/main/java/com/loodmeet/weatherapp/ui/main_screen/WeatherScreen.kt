package com.loodmeet.weatherapp.ui.main_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.ui.models.Weather

@Composable
fun WeatherScreen(weather: Weather) {

    Surface(color = Color.Transparent, contentColor = MaterialTheme.colorScheme.onBackground) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val dailyWeatherCardModifier = Modifier
                .weight(1f)
                .padding(vertical = 30.dp)

            WeatherCard(modifier = dailyWeatherCardModifier, weather = weather)

            Surface(
                color = Color.Transparent,
                modifier = Modifier.weight(1.5f)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    val dividerModifier = Modifier.fillMaxWidth(1f)
                    val rowModifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()

                    SunriseAndSunset(modifier = rowModifier, weather = weather)
                    Divider(dividerModifier)
                    ApparentTemperature(modifier = rowModifier, weather = weather)
                    Divider(dividerModifier)
                    Wind(modifier = rowModifier, weather = weather)
                    Divider(dividerModifier)
                    PrecipitationSum(modifier = rowModifier, weather = weather)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherCard(modifier: Modifier = Modifier, weather: Weather) {

    var isDaily by remember { mutableStateOf(true) }

    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(6.dp), onClick = {
            isDaily = !isDaily
        }
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_info_24),
                    contentDescription = null
                )
            }

            Row {
                val weatherModifier = Modifier
                    .padding(bottom = 30.dp, top = 10.dp)
                    .fillMaxWidth()
                if (isDaily) {
                    TopDailyWeather(
                        weather = weather,
                        modifier = weatherModifier
                    )
                } else {
                    TopHourlyWeather(
                        weather = weather,
                        modifier = weatherModifier
                    )
                }
            }
        }
    }
}

@Composable
fun TopHourlyWeather(
    modifier: Modifier = Modifier,
    weather: Weather,
    iconSize: Dp = 56.dp,
) = with(MaterialTheme.colorScheme) {

    LazyRow(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 5.dp)
    ) {
        items(weather.hourlyWeather) { item ->
            OutlinedCard(modifier = Modifier.padding(horizontal = 5.dp)) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(5.dp)
                        .width(80.dp)
                ) {
                    Text(
                        text = item.time,
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSecondaryContainer,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(iconSize),
                        tint = onPrimaryContainer
                    )
                    Text(
                        text = "${item.temperature}°",
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSecondaryContainer
                    )
                    Text(
                        text = stringResource(id = item.descriptionResId),
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSecondaryContainer,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopDailyWeather(
    modifier: Modifier = Modifier,
    iconSize: Dp = 48.dp,
    weather: Weather
) = with(MaterialTheme.colorScheme) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = weather.iconResId),
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = onPrimaryContainer
            )
            Text(
                text = stringResource(id = weather.descriptionResId),
                style = MaterialTheme.typography.bodyLarge,
                color = onSecondaryContainer
            )
        }

        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight(), color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "${weather.temperatureMin}° / ${weather.temperatureMax}°",
                style = MaterialTheme.typography.headlineMedium,
                color = onSecondaryContainer
            )
        }
    }
}

@Composable
fun SunriseAndSunset(
    modifier: Modifier = Modifier,
    progress: Float = 0.6f,
    weather: Weather
) {

    val progressIndicatorModifier = Modifier
        .fillMaxWidth(fraction = 0.85f)
        .height(4.dp)
        .clip(RoundedCornerShape(5.dp))

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextRow {
            Text(text = "${stringResource(R.string.sunrise)}: ${weather.sunrise}")
            Text(text = "${stringResource(R.string.sunset)}: ${weather.sunset}")
        }
        LinearProgressIndicator(
            progress = progress,
            modifier = progressIndicatorModifier
        )
    }
}

@Composable
fun ApparentTemperature(modifier: Modifier = Modifier, weather: Weather) {

    TextRow(modifier = modifier) {
        Text(
            text = "${stringResource(R.string.apparent_temperature)}: ",
            textAlign = TextAlign.Center
        )
        Text(text = "${weather.apparentTemperatureMin}° / ${weather.apparentTemperatureMax}°")
    }
}

@Composable
fun Wind(modifier: Modifier = Modifier, weather: Weather) {

    TextRow(modifier = modifier) {
        Text(text = "${stringResource(R.string.wind)}:")
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "${weather.windSpeed} ${stringResource(id = weather.measurementUnitsSet.windSpeedUnit.unitResId)}",
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PrecipitationSum(modifier: Modifier = Modifier, weather: Weather) {

    TextRow(modifier = modifier) {
        Text(text = "${stringResource(R.string.precipitation_sum)}:")
        Text(
            text = "${weather.precipitationSum} ${stringResource(id = weather.measurementUnitsSet.precipitationUnit.unitResId)}"
        )
    }
}

@Composable
fun TextRow(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = content
    )
}
