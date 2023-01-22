package com.loodmeet.weatherapp.ui.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmitLugg.weatherapp.R
import com.loodmeet.weatherapp.core.ui.composable.TextRow
import com.loodmeet.weatherapp.core.ui.composable.VerticalDivider
import com.loodmeet.weatherapp.core.ui.models.Units
import com.loodmeet.weatherapp.core.ui.models.UnitsOfMeasurementResIds
import com.loodmeet.weatherapp.feature_daily_weather.ui.models.DailyWeather


val units = UnitsOfMeasurementResIds(
    temperatureUnitResId = Units.TemperatureUnits.CELSIUS,
    windSpeedUnitResId = Units.WindSpeedUnits.METRES_PER_SECOND,
    precipitationUnitResId = Units.PrecipitationUnits.MILLIMETER
)

val hourlyWeather =
    com.loodmeet.weatherapp.core.ui.models.HourlyWeather("", R.drawable.ic_person_outlined, "")
val dailyWeather = DailyWeather(
    descriptionResId = R.string.clear_sky,
    iconResId = R.drawable.ic_person_outlined,
    temperatureMax = 1,
    temperatureMin = 1,
    sunrise = "1",
    sunset = "1",
    apparentTemperatureMax = 1,
    apparentTemperatureMin = 1,
    windSpeed = 1.1,
    windDirectionIconResId = R.drawable.ic_person_outlined,
    precipitationSum = 1,
    hourlyWeather = listOf(hourlyWeather),
    unitsOfMeasurementResIds = units
)

@Composable
fun Screen(index: Int) {

    val dividerModifier = Modifier.fillMaxWidth(1f)

    Surface {
        Column(
            modifier = Modifier.fillMaxSize().padding(30.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 30.dp)
            ) {
                TopDailyWeather(dailyWeather = dailyWeather)
            }
            Divider(dividerModifier)
            SunriseAndSunset(modifier = Modifier.weight(0.5f))
            Divider(dividerModifier)
            ApparentTemperature(modifier = Modifier.weight(0.5f))
            Divider(dividerModifier)
            Wind(modifier = Modifier.weight(0.5f))
            Divider(dividerModifier)
            PrecipitationSum(modifier = Modifier.weight(0.5f))
        }
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
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
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
                .weight(1f)
        ) {
            Text(
                text = "${dailyWeather.temperatureMin}° / ${dailyWeather.temperatureMax}°",
                fontSize = temperatureFontSize
            )
        }

    }

}

@Composable
fun SunriseAndSunset(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextRow {
            Text(text = "${stringResource(R.string.sunrise)}: ${dailyWeather.sunrise}")
            Text(text = "${stringResource(R.string.sunset)}: ${dailyWeather.sunset}")
        }
        LinearProgressIndicator(
            progress = 0.6f,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
        )
    }
}

@Composable
fun ApparentTemperature(modifier: Modifier = Modifier) {
    TextRow(modifier = modifier) {
        Text(
            text = "${stringResource(R.string.apparent_temperature)}: ",
            textAlign = TextAlign.Center
        )
        Text(text = "${dailyWeather.apparentTemperatureMin}° / ${dailyWeather.apparentTemperatureMax}°")
    }
}

@Composable
fun Wind(modifier: Modifier = Modifier) {

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
                text = "${dailyWeather.windSpeed} ${
                    stringResource(
                        id = dailyWeather.unitsOfMeasurementResIds.windSpeedUnitResId
                    )
                }",
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PrecipitationSum(modifier: Modifier = Modifier) {

    TextRow(modifier = modifier) {
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
