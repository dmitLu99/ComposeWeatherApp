package com.loodmeet.weatherapp.core.models

data class Settings(
    var measurementUnits: MeasurementUnitsSet,
    var location: Location,
    var language: Language,
    var theme: Theme
)