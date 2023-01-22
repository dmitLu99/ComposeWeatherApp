package com.loodmeet.weatherapp.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF7fd0ff),
    secondary = Color(0xFFb6c9d8),
    tertiary = Color(0xFFcbc1e9),

    background = Color(0xFF191c1e),
    surface = Color(0xFF191c1e),
    onPrimary = Color(0xFF00344a),
    onSecondary = Color(0xFF20333e),
    onTertiary = Color(0xFF322c4c),
    onBackground = Color(0xFFe1e2e5),
    onSurface = Color(0xFFe1e2e5),
    primaryContainer = Color(0xFF004c6a),
    secondaryContainer = Color(0xFF374955),
    onSecondaryContainer = Color(0xFFd1e5f4),
//    onSurfaceVariant = Color(0xFFc1c7ce)
    onSurfaceVariant = Color(0xFFdde3ea)

)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006685),
    secondary = Color(0xFF8dabbb),
    tertiary = Color(0xFF5d5b7d),

    background = Color.White,
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color.Black,
    primaryContainer = Color(0xFFcee5ff),
    secondaryContainer = Color(0xFFd1e5f4),
    onSecondaryContainer = Color(0xFF001f2a),
//    onSurfaceVariant = Color(0xFF41484d)
            onSurfaceVariant = Color(0xFF41484d)

    /**/
)

@Composable
fun ComposeWeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    val systemUiController = rememberSystemUiController()
    if (!view.isInEditMode) {
        SideEffect {

            systemUiController.setStatusBarColor(color = Color.Transparent)
            //window.statusBarColor = colorScheme.primary.toArgb()
            //ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}