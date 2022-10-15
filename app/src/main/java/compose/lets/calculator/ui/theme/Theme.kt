package compose.lets.calculator.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import compose.lets.calculator.MainActivity

private val DarkColorScheme = darkColorScheme(
    primary = DarkBackground,
    secondary = darkCardBack,
    tertiary = textDark,
     background = DarkBackground,
    onPrimary = Color(0xFFFFFFFF),
    onBackground = SheetDark
)

private val LightColorScheme = lightColorScheme(
    primary = LightBackground,
    secondary = lightCardBack,
    tertiary = textLight,
    background = LightBackground,
    onPrimary = Color(0xFF000000),
    onBackground = SheetLight

)


@Composable
fun CalculatorTheme(
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