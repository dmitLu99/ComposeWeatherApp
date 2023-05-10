package com.loodmeet.weatherapp.ui.models

import androidx.compose.runtime.Composable

data class MainScreenTabItem(val title: String, val screen: @Composable () -> Unit)
