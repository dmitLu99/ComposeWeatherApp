package com.dmitLugg.weatherapp.feature_daily_weather.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class HeaderShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
//            addRect(Rect(
//                Offset(0f, 0f),
//                Offset(size.width, size.height / 3.5f)
//            ))
            addArcRad(
                Rect(
                    Offset(0f - size.width * 0.1f, 0 - size.height * 0.5f),
                    Offset(size.width * 1.1f, size.height * 1f)
                ),0f, 2 * Math.PI.toFloat()
            )


//            addOval(
//                Rect(
//                    Offset(0f - size.width * 0.1f, 0 - size.height * 0.5f),
//                    Offset(size.width * 1.1f, size.height * 1f)
//                )
//            )
        }
        return Outline.Generic(path)
    }
}