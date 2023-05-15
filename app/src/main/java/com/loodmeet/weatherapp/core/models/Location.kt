package com.loodmeet.weatherapp.core.models

import com.dmitLugg.weatherapp.R

open class Location private constructor(
    val cityResId: Int,
    val countryResId: Int,
    val latitude: Double,
    val longitude: Double,
    val maxDayLengthInHours: Double
) : Named {

    override val nameResId: Int
        get() = cityResId

    object Moscow : Location(R.string.city_moscow, R.string.country_russia, 55.75,37.62, 17.5)
    object Volgograd : Location(R.string.city_volgograd, R.string.country_russia, 48.72, 44.50, 16.15)
    object Berlin : Location(R.string.city_berlin, R.string.country_germany, 52.52,13.41, 16.85)
    object Paris : Location(R.string.city_paris, R.string.country_france, 48.85, 2.35, 16.15)
    object Washington : Location(R.string.city_washington, R.string.country_usa, 38.9, -77.04, 14.9)

    companion object {
        fun getList() = listOf(
            Moscow, Volgograd, Berlin, Paris, Washington
        )
    }
}
