package com.loodmeet.weatherapp.core.models

import com.dmitLugg.weatherapp.R

@Deprecated("")
data class UnitsOfMeasurementResIds(
    val temperatureUnitResId: Int,
    val windSpeedUnitResId: Int,
    val precipitationUnitResId: Int
) {
    companion object {
        object TemperatureUnits {
            const val CELSIUS = R.string.celsius
            const val FAHRENHEIT = R.string.fahrenheit
        }

        object WindSpeedUnits {
            const val METRES_PER_SECOND = R.string.metres_per_second
            const val KILOMETRES_PER_HOUR = R.string.kilometres_per_hour
            const val MILES_PER_HOUR = R.string.miles_per_hour
            const val KNOTS = R.string.knots
        }

        object PrecipitationUnits {
            const val MILLIMETER = R.string.millimeter
            const val INCH = R.string.inch
        }
    }
}
interface Named {
    val nameResId: Int
}
open class UnitOfMeasurement(
    open val unitResId: Int,
    open val requestName: String
) : Named {

    override val nameResId: Int
        get() = unitResId

    open class TemperatureUnit(
        override val unitResId: Int,
        override val requestName: String
    ) : UnitOfMeasurement(unitResId = unitResId, requestName = requestName) {
        object Celsius : TemperatureUnit(
            unitResId = R.string.celsius,
            requestName = ""
        )
        object Fahrenheit : TemperatureUnit(
            unitResId = R.string.fahrenheit,
            requestName = "fahrenheit"
        )
    }
    open class WindSpeedUnit(
        override val unitResId: Int,
        override val requestName: String
    ) : UnitOfMeasurement(unitResId = unitResId, requestName = requestName) {
        object KilometresPerHour : WindSpeedUnit(
            unitResId = R.string.kilometres_per_hour,
            requestName = ""
        )
        object MetresPerSecond : WindSpeedUnit(
            unitResId = R.string.metres_per_second,
            requestName = "ms"
        )
        object MilesPerHour : WindSpeedUnit(
            unitResId = R.string.miles_per_hour,
            requestName = "mph"
        )
        object Knots : WindSpeedUnit(
            unitResId = R.string.knots,
            requestName = "kn"
        )
    }
    open class PrecipitationUnit(
        override val unitResId: Int,
        override val requestName: String
    ) : UnitOfMeasurement(unitResId = unitResId, requestName = requestName) {
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