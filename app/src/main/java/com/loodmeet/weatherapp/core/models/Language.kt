package com.loodmeet.weatherapp.core.models

import com.dmitLugg.weatherapp.R

sealed class Language private constructor(
    private val langNameId: Int,
    private val tag: String
) : Named {

    override val nameResId: Int
        get() = langNameId

    fun getTag(): String = tag
    object Russian : Language(R.string.language_russian, "ru")
    object English : Language(R.string.language_english, "en")

    companion object {
        fun getList() = listOf(
            Russian, English
        )
    }
}