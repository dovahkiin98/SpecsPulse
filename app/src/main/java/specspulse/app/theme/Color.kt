package specspulse.app.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import specspulse.app.R

internal val LightColorScheme: ColorScheme
    @Composable get() = lightColorScheme(
        primary = colorResource(id = R.color.colorSecondary),
        secondary = colorResource(id = R.color.colorSecondary),
        surface = Color(0xFFfdfcff),
        surfaceContainer = Color(0xFFfdf0ef),
        surfaceContainerHighest = Color(0xFFfdf0ef),
        background = Color(0xFFfdfcff),
    )

internal val DarkColorScheme: ColorScheme
    @Composable get() = darkColorScheme(
        primary = colorResource(id = R.color.colorSecondary),
        secondary = colorResource(id = R.color.colorSecondary),
        surface = Color(0xFF1a1c1e),
        surfaceContainer = Color(0xFF2a201e),
        surfaceContainerHighest = Color(0xFF2a201e),
        background = Color(0xFF1a1c1e),
    )