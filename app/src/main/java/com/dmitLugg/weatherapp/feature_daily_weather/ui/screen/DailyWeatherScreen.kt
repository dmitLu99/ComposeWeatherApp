package com.dmitLugg.weatherapp.feature_daily_weather.ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.dmitLugg.weatherapp.R
import com.dmitLugg.weatherapp.app.ui.theme.ComposeWeatherAppTheme
import com.dmitLugg.weatherapp.feature_daily_weather.shapes.HeaderShape
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.Date
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.HourlyWeather
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.Location
import java.time.LocalTime

@[Preview(showBackground = true) Composable]
fun DailyWeatherScreen(onNavigateBack: () -> Unit = {}) {
    ComposeWeatherAppTheme {
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
                        AppBar(Location("Russia", "Volgograd"), onNavigateBack = onNavigateBack)
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

            DailyWeather(hourlyWeatherList = List(size = 23) { index ->
                HourlyWeather(time = LocalTime.of(index, 0).toString(), "", index.toString())
            })
        }
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
            text = "On This Week",
            fontSize = 22.sp,
            style = TextStyle(color = MaterialTheme.colorScheme.onTertiary)
        )
    }
}

@Composable
fun Calendar(days: List<Date>) {
    val fontSize = 20.sp

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
fun DailyWeather(hourlyWeatherList: List<HourlyWeather>) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                )
                Text(text = "Cloudy", modifier = Modifier)
            }

            VerticalDivider(horizontalPadding = 20.dp, verticalPadding = 10.dp)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = "-20° / -17°", fontSize = 25.sp)
            }

        }
        TextRow {
            Text(text = "Sunrise: 7:40")
            Text(text = "Sunset: 18:20")
        }
        Divider()
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
        Divider()

        TextRow {
            Text(text = "Apparent temperature: ", textAlign = TextAlign.Center)
            Text(text = "-24° / -20°")
        }


        TextRow {
            Text(text = "Wind: ")
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "3.4 m/s", modifier = Modifier)
            }
        }
        TextRow {
            Text(text = "Precipitation Sum: ")
            Text(text = "140 mm")

        }


        Spacer(modifier = Modifier.height(10.dp))

    }


}

@Composable
fun TextRow(horizontalPadding: Dp = 20.dp, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = content
    )
}

@Composable
fun VerticalDivider(horizontalPadding: Dp = 10.dp, verticalPadding: Dp = 0.dp) {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .width(horizontalPadding * 2 + 1.dp)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    )
}