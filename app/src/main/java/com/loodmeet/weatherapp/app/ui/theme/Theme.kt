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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8ecdff),
    secondary = Color(0xFFb6c9d8),
    tertiary = Color(0xFFcbc1e9),
    background = Color(0xFF191c1e),
    surface = Color(0xFF001f2a),
    onPrimary = Color(0xFF00344f),
    onSecondary = Color(0xFF20333e),
    onTertiary = Color(0xFF322c4c),
    onBackground = Color(0xFFe1e2e5),
    onSurface = Color(0xFFbfe9ff),
    primaryContainer = Color(0xFF004c6a),
    secondaryContainer = Color(0xFF161c22),
    onSecondaryContainer = Color(0xFFd1e5f4),
    onSurfaceVariant = Color(0xFFdde3ea),
    tertiaryContainer = Color(0xFF41474d),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006494),
    secondary = Color(0xFF8dabbb),
    tertiary = Color(0xFF5d5b7d),
    background = Color.White,
    surface = Color(0xFFfafcff),
    onPrimary = Color(0xFFffffff),
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color.Black,
    primaryContainer = Color(0xFFcee5ff),
    secondaryContainer = Color(0xFFf6f9ff),
    onSecondaryContainer = Color(0xFF001f2a),
    onSurfaceVariant = Color(0xFF41484d),
    tertiaryContainer = Color(0xFF001f2a),
)

@Composable
fun ComposeWeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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
    WindowCompat.setDecorFitsSystemWindows(window, true)
    val systemUiController = rememberSystemUiController()
    if (!view.isInEditMode) {
        SideEffect {
            systemUiController.setStatusBarColor(color = colorScheme.surface, darkIcons = !darkTheme)
            systemUiController.setNavigationBarColor(colorScheme.background)

        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}