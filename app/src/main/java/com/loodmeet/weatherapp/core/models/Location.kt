package com.loodmeet.weatherapp.core.models

import com.dmitLugg.weatherapp.R

open class Location private constructor(
    val cityResId: Int,
    val countryResId: Int,
    val latitude: Double,
    val longitude: Double
) : Named {

    override val nameResId: Int
        get() = cityResId

    object Moscow : Location(R.string.city_moscow, R.string.country_russia, 55.75,37.62)
    object Volgograd : Location(R.string.city_volgograd, R.string.country_russia, 48.72, 44.50)
    object Berlin : Location(R.string.city_berlin, R.string.country_germany, 52.52,13.41)
    object London : Location(R.string.city_london, R.string.country_england, 51.51, -0.13, )
    object Washington : Location(R.string.city_washington, R.string.country_usa, 38.90, -77.04)

    companion object {
        fun getList() = listOf(
            Moscow, Volgograd, Berlin, London, Washington
        )
    }
}
