package com.loodmeet.weatherapp.core.utils

import android.util.Log

object Config {

    object LOG {

        const val UI_LOG_TAG = "UILogger"

        fun logDebug(message: String) { Log.d(UI_LOG_TAG, message) }
    }

    object NAVIGATION {

        const val MAIN_SCREEN = "mainScreen"
    }
}
