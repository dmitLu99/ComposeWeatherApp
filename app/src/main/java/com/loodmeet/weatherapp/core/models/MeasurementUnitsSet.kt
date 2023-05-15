package com.loodmeet.weatherapp.core.models

data class MeasurementUnitsSet(
    var temperatureUnit: MeasurementUnit.TemperatureUnit = MeasurementUnit.TemperatureUnit.Celsius,
    var windSpeedUnit: MeasurementUnit.WindUnitSpeedUnit = MeasurementUnit.WindUnitSpeedUnit.MetresPerSecond,
    var precipitationUnit: MeasurementUnit.PrecipitationUnit = MeasurementUnit.PrecipitationUnit.Millimeter
)