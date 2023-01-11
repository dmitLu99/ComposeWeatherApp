package com.dmitLugg.weatherapp.core.ui.models

data class UnitsOfMeasurement(
    val temperatureUnit: Units.TemperatureUnits,
    val windSpeedUnit: Units.WindSpeedUnits,
    val precipitationUnit: Units.PrecipitationUnits
)