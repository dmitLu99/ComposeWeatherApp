package com.loodmeet.weatherapp.core.models

import com.dmitLugg.weatherapp.R

sealed class Theme private constructor(
    private val nameId: Int
) : Named {

    override val nameResId: Int
        get() = nameId

    object Dark : Theme(R.string.theme_dark)
    object Light : Theme(R.string.theme_light)
    object System : Theme(R.string.theme_system)

    companion object {
        fun getList() = listOf(
            Dark, Light, System
        )
    }
}