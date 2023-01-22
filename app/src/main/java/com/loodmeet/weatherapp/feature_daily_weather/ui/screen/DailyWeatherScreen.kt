package com.loodmeet.weatherapp.feature_daily_weather.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.ui.composable.HourlyItem
import com.loodmeet.weatherapp.core.ui.composable.TextRow
import com.loodmeet.weatherapp.core.ui.composable.VerticalDivider
import com.loodmeet.weatherapp.feature_daily_weather.ui.shapes.HeaderShape
import com.loodmeet.weatherapp.feature_main_screen.ui.models.Date
import com.loodmeet.weatherapp.feature_daily_weather.ui.models.DailyWeather
import com.loodmeet.weatherapp.feature_daily_weather.ui.view_models.DailyWeatherViewModel
import com.loodmeet.weatherapp.feature_main_screen.ui.models.Location

@[Preview(showBackground = true) Composable]
fun DailyWeatherScreen(onNavigateBack: () -> Unit = {}) {

    val location = Location("Russia", "Volgograd")

    val viewModel: DailyWeatherViewModel = viewModel()
//    val dailyWeather = remember { mutableStateOf(viewModel.data) }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(shape = RectangleShape)
                    .padding(bottom = 16.dp), shadowElevation = 10.dp,
                shape = HeaderShape()
            ) {
                Image(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .clip(shape = HeaderShape()),
                    painter = painterResource(id = R.drawable.main_bg7_header_4),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    AppBar(location, onNavigateBack = onNavigateBack)
                    Spacer(modifier = Modifier.height(20.dp))
                    Header()
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
            )
            Calendar(days = List(size = 7) { index ->
                Date(
                    dayOfWeek = "Mon", dayOfMonth = "${9 + index}"
                )
            })
            Divider()
        }

//        DailyWeather(dailyWeather = viewModel.data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(location: Location, onNavigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = location.country + ", " + location.city,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onTertiary
        ),
    )
}

@Composable
fun Header() {
    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.on_this_week),
            fontSize = 22.sp,
            style = TextStyle(color = MaterialTheme.colorScheme.onTertiary)
        )
    }
}

@Composable
fun Calendar(days: List<Date>, fontSize: TextUnit = 20.sp) {

    val viewModel = viewModel<DailyWeatherViewModel>()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val clicked = remember { mutableStateOf(0) }
            for (number in days.indices) {

                val interactionSource = remember { MutableInteractionSource() }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = days[number].dayOfWeek, fontSize = fontSize, color = Color.Gray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                            .background(
                                color = if (clicked.value == number) {
                                    MaterialTheme.colorScheme.primary
                                } else Color.Transparent
                            )
                            .clickable(
                                interactionSource = interactionSource, indication = null
                            ) {
                                clicked.value = number
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = days[number].dayOfMonth,
                            fontSize = fontSize,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = if (clicked.value == number) {
                                    Color.White
                                } else Color.Black
                            )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun TopDailyWeather(
    modifier: Modifier = Modifier,
    temperatureFontSize: TextUnit = 25.sp,
    iconSize: Dp = 48.dp,
    dailyWeather: DailyWeather
) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
            )
            Text(text = stringResource(id = dailyWeather.descriptionResId), modifier = Modifier)
        }

        VerticalDivider(horizontalPadding = 20.dp, verticalPadding = 10.dp)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(
                text = "${dailyWeather.temperatureMin}° / ${dailyWeather.temperatureMax}°",
                fontSize = temperatureFontSize
            )
        }

    }
    TextRow {
        Text(text = "${stringResource(R.string.sunrise)}: ${dailyWeather.sunrise}")
        Text(text = "${stringResource(R.string.sunset)}: ${dailyWeather.sunset}")
    }
}

@Composable
fun BottomDailyWeather(dailyWeather: DailyWeather) {
    TextRow {
        Text(
            text = "${stringResource(R.string.apparent_temperature)}: ",
            textAlign = TextAlign.Center
        )
        Text(text = "${dailyWeather.apparentTemperatureMin}° / ${dailyWeather.apparentTemperatureMax}°")
    }


    TextRow {
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
                text = "${dailyWeather.windSpeed} ${
                    stringResource(
                        id = dailyWeather.unitsOfMeasurementResIds.windSpeedUnitResId
                    )
                }",
                modifier = Modifier
            )
        }
    }
    TextRow {
        Text(text = stringResource(R.string.precipitation_sum))
        Text(
            text = "${dailyWeather.precipitationSum} ${
                stringResource(
                    id = dailyWeather.unitsOfMeasurementResIds.precipitationUnitResId
                )
            }"
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DailyWeather(dailyWeather: DailyWeather) {

    AnimatedContent(targetState = dailyWeather) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {

            TopDailyWeather(dailyWeather = dailyWeather)

            Divider()

            LazyRow {
                items(dailyWeather.hourlyWeather) { hourlyWeather ->
                    HourlyItem(hourlyWeather = hourlyWeather)
                }
            }

            Divider()

            BottomDailyWeather(dailyWeather = dailyWeather)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}