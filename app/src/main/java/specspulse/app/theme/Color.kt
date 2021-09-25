package specspulse.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import specspulse.app.R

@Composable
fun colors() = if (isSystemInDarkTheme()) darkColors(
    primary = colorResource(id = R.color.colorSecondary),
    secondary = colorResource(id = R.color.colorSecondary),
)
else lightColors(
    primary = colorResource(id = R.color.colorSecondary),
    secondary = colorResource(id = R.color.colorSecondary),
)