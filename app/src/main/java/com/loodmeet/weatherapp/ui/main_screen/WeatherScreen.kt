package com.loodmeet.weatherapp.ui.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
                .weight(1.2f)
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
        Box(
            Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier.blur(radius = 4.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = weather.translatedDailyWeather.backgroundId),
                contentDescription = null
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp)
                ) {
                    if (isDaily) IconButton(
                        modifier = Modifier.height(24.dp),
                        onClick = { isDaily = !isDaily }) {
                        Image(
                            painter = painterResource(id = R.drawable.outline_info_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                blendMode = BlendMode.Modulate, color = colorResource(
                                    id = weather.translatedDailyWeather.foregroundColorId
                                )
                            )
                        )
                    }
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
}

@Composable
fun TopHourlyWeather(
    modifier: Modifier = Modifier,
    weather: Weather,
    iconSize: Dp = 56.dp,
) = with(MaterialTheme.colorScheme) {
    val degree = stringResource(weather.measurementUnitsSet.temperatureUnit.unitResId);

    LazyRow(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 5.dp)
    ) {
        items(weather.hourlyWeather) { item ->
            OutlinedCard(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxHeight(fraction = 1f),
                colors = CardDefaults.cardColors(containerColor = Color(0x60000000))
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(5.dp)
                        .width(110.dp)
                ) {
                    Text(
                        text = item.time,
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSecondaryContainer,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(id = item.iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(iconSize),
                        colorFilter = ColorFilter.tint(
                            blendMode = BlendMode.Modulate,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "${item.temperature}${degree}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSecondaryContainer
                    )
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(id = item.descriptionResId),
                            style = MaterialTheme.typography.bodyLarge,
                            color = onSecondaryContainer,
                            modifier = Modifier.padding(vertical = 3.dp),
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    val modifier: Modifier = Modifier
    val iconSize: Dp = 48.dp
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        OutlinedCard(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .height(220.dp)
                .width(350.dp)
        ) {
            Row(
                modifier = modifier,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_sky_32),
                        contentDescription = null,
                        modifier = Modifier.size(iconSize),
                        colorFilter = ColorFilter.tint(
                            blendMode = BlendMode.Modulate,
                            color = Color.White
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.partly_cloudy),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }

                Divider(
                    modifier = Modifier
                        .background(Color.Green)
                        .rotate(45f)
                        .padding(top = 40.dp, bottom = 50.dp, start = 2.dp, end = 2.dp)
                        .height(130.dp)
                        .width(3.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer

                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1.3f)
                ) {
                    Text(
                        text = "10°F / 10°F",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Cyan,
                        modifier = Modifier
                            .padding(vertical = 50.dp)
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
    val degree = stringResource(weather.measurementUnitsSet.temperatureUnit.unitResId);

    val foreground = colorResource(id = weather.translatedDailyWeather.foregroundColorId)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = weather.translatedDailyWeather.dayImageResId),
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                colorFilter = ColorFilter.tint(blendMode = BlendMode.Modulate, color = foreground)
            )
            Text(
                text = stringResource(id = weather.translatedDailyWeather.stringResId),
                style = MaterialTheme.typography.bodyLarge,
                color = foreground,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }

        Divider(
            modifier = Modifier
                .padding(2.dp)
                .width(1.dp)
                .fillMaxHeight(),
            color = foreground,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .weight(1.3f)
        ) {
            Text(
                text = "${weather.temperatureMin}${degree} / ${weather.temperatureMax}${degree}",
                style = MaterialTheme.typography.headlineSmall,
                color = foreground
            )
        }
    }
}

@Composable
fun SunriseAndSunset(
    modifier: Modifier = Modifier,
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
            progress = weather.dayLengthIndicator,
            modifier = progressIndicatorModifier
        )
    }
}

@Composable
fun ApparentTemperature(modifier: Modifier = Modifier, weather: Weather) {

    val degree = stringResource(weather.measurementUnitsSet.temperatureUnit.unitResId);
    TextRow(modifier = modifier) {
        Text(
            text = "${stringResource(R.string.apparent_temperature)}: ",
            textAlign = TextAlign.Center
        )
        Text(text = "${weather.apparentTemperatureMin}${degree} / ${weather.apparentTemperatureMax}${degree}")
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
//            Icon(
//                imageVector = Icons.Outlined.ArrowBack,
//                contentDescription = null,
//                modifier = Modifier.size(24.dp)
//            )
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
