package com.dmitLugg.weatherapp.core.ui.models

import com.dmitLugg.weatherapp.R

object Units {

    enum class TemperatureUnits(val stringResId: Int) {
        CELSIUS(R.string.celsius), FAHRENHEIT(R.string.fahrenheit)
    }

    enum class WindSpeedUnits(val stringResId: Int) {
        METRES_PER_SECOND(R.string.metres_per_second), KILOMETRES_PER_HOUR(R.string.kilometres_per_hour),
        MILES_PER_HOUR(R.string.miles_per_hour), KNOTS(R.string.knots)
    }

    enum class PrecipitationUnits(val stringResId: Int) {
        MILLIMETER(R.string.millimeter), INCH(R.string.inch)
    }
}