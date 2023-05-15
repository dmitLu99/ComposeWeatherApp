package com.loodmeet.weatherapp.core.utils

import kotlin.math.roundToInt

class Temperature(
    private val value: Double,
    private var degreeSign: String = "°"
) {
    fun changeDegreeSign(degreeSign: String): Temperature {
        this.degreeSign = degreeSign
        return this
    }

    fun getValueAsString() =
        if (value.roundToInt() > 0) "+${value.roundToInt()}$degreeSign"
        else value.roundToInt().toString() + degreeSign

    fun getValueAsDouble() = value
}