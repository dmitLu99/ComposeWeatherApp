package com.loodmeet.weatherapp.ui.main_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.loodmeet.weatherapp.core.models.UnitsOfMeasurementResIds
import com.loodmeet.weatherapp.core.models.UnitsOfMeasurementResIds.Companion.TemperatureUnits.CELSIUS
import com.loodmeet.weatherapp.core.models.UnitsOfMeasurementResIds.Companion.WindSpeedUnits.METRES_PER_SECOND
import com.loodmeet.weatherapp.core.models.UnitsOfMeasurementResIds.Companion.PrecipitationUnits.MILLIMETER

data class DailyWeather(
    val descriptionResId: Int,
    val iconResId: Int,
    val temperatureMax: Int,
    val temperatureMin: Int,
    val sunrise: String,
    val sunset: String,
    val apparentTemperatureMin: Int,
    val apparentTemperatureMax: Int,
    val windSpeed: Double,
    val windDirectionIconResId: Int,
    val precipitationSum: Int,
    val unitsOfMeasurementResIds: UnitsOfMeasurementResIds
)

val units = UnitsOfMeasurementResIds(
    temperatureUnitResId = CELSIUS,
    windSpeedUnitResId = METRES_PER_SECOND,
    precipitationUnitResId = MILLIMETER
)

val dailyWeather = DailyWeather(
    descriptionResId = R.string.cloudy,
    iconResId = R.drawable.ic_sky_32,
    temperatureMax = 10,
    temperatureMin = 10,
    sunrise = "10:10",
    sunset = "10:10",
    apparentTemperatureMax = 10,
    apparentTemperatureMin = 10,
    windSpeed = 10.1,
    windDirectionIconResId = R.drawable.ic_person_outlined,
    precipitationSum = 10,
    unitsOfMeasurementResIds = units
)

@Composable
fun Screen() {

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

            DailyWeatherCard(modifier = dailyWeatherCardModifier, dailyWeather = dailyWeather)

            Surface(
                color = Color.Transparent,
                modifier = Modifier.weight(1.5f)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    val dividerModifier = Modifier.fillMaxWidth(1f)
                    val rowModifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()

                    SunriseAndSunset(modifier = rowModifier)
                    Divider(dividerModifier)
                    ApparentTemperature(modifier = rowModifier)
                    Divider(dividerModifier)
                    Wind(modifier = rowModifier)
                    Divider(dividerModifier)
                    PrecipitationSum(modifier = rowModifier)
                }
            }
        }
    }
}

@Composable
fun DailyWeatherCard(modifier: Modifier = Modifier, dailyWeather: DailyWeather) {

    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        TopDailyWeather(
            dailyWeather = dailyWeather,
            modifier = Modifier.padding(vertical = 40.dp)
        )
    }
}

@Composable
fun TopDailyWeather(
    modifier: Modifier = Modifier,
    iconSize: Dp = 48.dp,
    dailyWeather: DailyWeather
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
                painter = painterResource(id = dailyWeather.iconResId),
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = onPrimaryContainer
            )
            Text(
                text = stringResource(id = dailyWeather.descriptionResId),
                style = MaterialTheme.typography.bodyLarge,
                color = onSecondaryContainer
            )
        }

        Divider(modifier = Modifier
            .width(1.dp)
            .fillMaxHeight())

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "${dailyWeather.temperatureMin}° / ${dailyWeather.temperatureMax}°",
                style = MaterialTheme.typography.headlineMedium,
                color = onSecondaryContainer
            )
        }
    }
}

@Composable
fun SunriseAndSunset(modifier: Modifier = Modifier, progress: Float = 0.6f) {

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
            Text(text = "${stringResource(R.string.sunrise)}: ${dailyWeather.sunrise}")
            Text(text = "${stringResource(R.string.sunset)}: ${dailyWeather.sunset}")
        }
        LinearProgressIndicator(
            progress = progress,
            modifier = progressIndicatorModifier
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
        Text(text = "${stringResource(R.string.precipitation_sum)}:")
        Text(
            text = "${dailyWeather.precipitationSum} ${
                stringResource(
                    id = dailyWeather.unitsOfMeasurementResIds.precipitationUnitResId
                )
            }"
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
