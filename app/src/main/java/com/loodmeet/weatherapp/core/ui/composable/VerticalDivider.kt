package com.loodmeet.weatherapp.core.ui.composable

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDivider(horizontalPadding: Dp = 10.dp, verticalPadding: Dp = 0.dp) {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .width(horizontalPadding * 2 + 1.dp)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    )
}