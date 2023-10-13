package bme.hit.greenhouse.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    secondary = GreenGrey80,
    tertiary = GreenGrey80,
    surface = Green90,
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black,
    onTertiaryContainer = Green40,
    tertiaryContainer = Green100,
    primaryContainer = Green100,
    secondaryContainer = Green100,
    background = Green100
)

private val LightColorScheme = lightColorScheme(
    primary = Green80,
    secondary = GreenGrey80,
    tertiary = GreenGrey80,
    surface = Green90,
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black,
    onTertiaryContainer = Green40,
    tertiaryContainer = Green100,
    primaryContainer = Green100,
    secondaryContainer = Green100,
    background = Green100
)

@Composable
fun GreenHouseTheme(
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
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}