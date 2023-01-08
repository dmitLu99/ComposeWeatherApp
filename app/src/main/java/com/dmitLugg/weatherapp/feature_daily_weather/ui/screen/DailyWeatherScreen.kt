package com.dmitLugg.weatherapp.feature_daily_weather.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.dmitLugg.weatherapp.R
import com.dmitLugg.weatherapp.app.ui.theme.ComposeWeatherAppTheme
import com.dmitLugg.weatherapp.feature_daily_weather.shapes.HeaderShape
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.Date
import com.dmitLugg.weatherapp.feature_main_screen.ui.models.Location

@[Preview(showBackground = true) Composable]
fun DailyWeatherScreen() {
    ComposeWeatherAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {

            Column {


                Box(
//                    shape = HeaderShape(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                    //shadowElevation = 26.dp
                ) {
                    Image(
                        modifier = Modifier
                            .clip(shape = HeaderShape())
                            .padding(bottom = 30.dp)
                            .shadow(12.dp, shape = HeaderShape(), clip = true)
                            .padding(bottom = 16.dp)
                            .clip(shape = HeaderShape()),

                        painter = painterResource(id = R.drawable.main_bg7_header_4),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = 1f
                    )



                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        AppBar(Location("Russia", "Volgograd"))
                        Spacer(
                            modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth()
                        )
                        Header()
                    }


                }

                Calendar(days = List(size = 7) { index ->
                    Date(
                        dayOfWeek = "Mon",
                        dayOfMonth = "${9 + index}"
                    )
                })
            }

            DailyWeather()


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(location: Location) {
    TopAppBar(
        title = {
            Text(
                text = location.country + ", " + location.city,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        ComposeWeatherAppTheme(darkTheme = true, dynamicColor = false) {
            Text(
                text = "On This Week",
                fontSize = 22.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}

@Composable
fun Calendar(days: List<Date>) {
    val fontSize = 20.sp
//    val backgroundColor = remember { mutableStateOf(Color.Transparent) }
    //val  = remember { mutableStateOf(Color.Transparent) }

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
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                clicked.value = number
                            },
                        contentAlignment = Alignment.Center
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
fun DailyWeather() {

}