package com.loodmeet.weatherapp.feature_main_screen.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.ui.composable.HourlyItem
import com.loodmeet.weatherapp.feature_main_screen.ui.models.CurrentWeather
import com.loodmeet.weatherapp.core.ui.models.HourlyWeather
import com.loodmeet.weatherapp.feature_main_screen.ui.models.Location
import com.loodmeet.weatherapp.feature_main_screen.ui.shapes.CustomShape
import java.time.LocalDate
import java.time.LocalTime

@[OptIn(ExperimentalMaterial3Api::class) Preview(showBackground = true) Composable]
fun MainScreen(onNavigateToDailyWeather: () -> Unit = {}) {

//    val viewModel: MainScreenViewModel = viewModel()

    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = MaterialTheme.colorScheme.onTertiary,
        navigationIconContentColor = MaterialTheme.colorScheme.onTertiary,
        actionIconContentColor = MaterialTheme.colorScheme.onTertiary
    )

    val location = Location("Russia", "Volgograd")

    val currentWeather = CurrentWeather(
        LocalDate.now().toString(),
        temperature = "19",
        description = "cloudy"
    )

    val hourlyWeatherList = List(size = 23) { index ->
        HourlyWeather(
            time = LocalTime.of(index, 0).toString(),
            R.drawable.ic_person_outlined,
            index.toString()
        )
    }

    val topShape = CustomShape()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f),
            shadowElevation = 6.dp,
            shape = topShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.main_bg7),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column {

                AppBar(location = location, colors = topAppBarColors)

                CurrentWeather(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 25.dp),
                    currentWeather = currentWeather
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            contentAlignment = Alignment.Center
        ) {
            HourlyWeather(
                onNavigateToDailyWeather = onNavigateToDailyWeather,
                hourlyWeatherList = hourlyWeatherList
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(location: Location, colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()) {
    TopAppBar(
        title = {
            Text(
                text = location.country + ", " + location.city,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                )
            }

        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.LocationOn, contentDescription = null,
                )
            }

        },
        colors = colors
    )
}

@Composable
fun CurrentWeather(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather,
    textStyle: TextStyle = TextStyle(color = MaterialTheme.colorScheme.onTertiary),
    dateFontSize: TextUnit = 22.sp,
    temperatureFontSize: TextUnit = 110.sp,
    descriptionFontSize: TextUnit = 22.sp,
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = currentWeather.date,
            fontSize = dateFontSize,
            style = textStyle
        )
        Text(
            text = currentWeather.temperature, fontSize = temperatureFontSize,
            style = textStyle
        )
        Text(
            text = currentWeather.description, fontSize = descriptionFontSize,
            style = textStyle
        )
    }
}

@Composable
fun HourlyWeather(
    hourlyWeatherList: List<HourlyWeather>,
    topFontSize: TextUnit = 20.sp,
    onNavigateToDailyWeather: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = stringResource(R.string.today), fontSize = topFontSize)

            Row(
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false),
                    onClick = onNavigateToDailyWeather
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.week), fontSize = topFontSize)
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }

        }
        LazyRow {
            items(hourlyWeatherList) { hourlyWeather ->
                HourlyItem(hourlyWeather = hourlyWeather)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}