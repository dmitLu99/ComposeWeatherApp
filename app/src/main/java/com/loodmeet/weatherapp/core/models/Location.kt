package com.loodmeet.weatherapp.core.models

import com.dmitLugg.weatherapp.R

open class Location private constructor(
    val cityResId: Int,
    val countryResId: Int,
    val maxDayLengthInHours: Double
) : Named {

    override val nameResId: Int
        get() = cityResId

    object Moscow : Location(R.string.city_moscow, R.string.country_russia,  17.5)
    object Volgograd : Location(R.string.city_volgograd, R.string.country_russia, 16.15)
    object Berlin : Location(R.string.city_berlin, R.string.country_germany,  16.85)
    object Paris : Location(R.string.city_paris, R.string.country_france,  16.15)
    object Washington : Location(R.string.city_washington, R.string.country_usa,  14.9)

    companion object {
        fun getList() = listOf(
            Moscow, Volgograd, Berlin, Paris, Washington
        )
    }
}
