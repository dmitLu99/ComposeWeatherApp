package com.loodmeet.weatherapp.core.models

import com.dmitLugg.weatherapp.R

interface Named {
    val nameResId: Int
}
open class MeasurementUnit(
    open val unitResId: Int,
    open val requestName: String
) : Named {

    override val nameResId: Int
        get() = unitResId

    open class TemperatureUnit private constructor(
        override val unitResId: Int,
        override val requestName: String
    ) : MeasurementUnit(unitResId = unitResId, requestName = requestName) {

        object Celsius : TemperatureUnit(
            unitResId = R.string.celsius,
            requestName = ""
        )
        object Fahrenheit : TemperatureUnit(
            unitResId = R.string.fahrenheit,
            requestName = "fahrenheit"
        )
    }
    open class WindUnitSpeedUnit private constructor(
        override val unitResId: Int,
        override val requestName: String
    ) : MeasurementUnit(unitResId = unitResId, requestName = requestName) {
        object KilometresPerHour : WindUnitSpeedUnit(
            unitResId = R.string.kilometres_per_hour,
            requestName = ""
        )
        object MetresPerSecond : WindUnitSpeedUnit(
            unitResId = R.string.metres_per_second,
            requestName = "ms"
        )
        object MilesPerHour : WindUnitSpeedUnit(
            unitResId = R.string.miles_per_hour,
            requestName = "mph"
        )
        object Knots : WindUnitSpeedUnit(
            unitResId = R.string.knots,
            requestName = "kn"
        )
    }
    open class PrecipitationUnit private constructor(
        override val unitResId: Int,
        override val requestName: String
    ) : MeasurementUnit(unitResId = unitResId, requestName = requestName) {
        object Millimeter : PrecipitationUnit(
            unitResId = R.string.millimeter,
            requestName = ""
        )
        object Inch : PrecipitationUnit(
            unitResId = R.string.inch,
            requestName = "inch"
        )
    }
}