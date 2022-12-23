package compose.lets.calculator.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = DarkBackground,
    secondary = darkCardBack,
    tertiary = textDark,
    background = DarkBackground,
    onPrimary = White,
    onBackground = SheetDark
)

private val LightColorScheme = lightColorScheme(
    primary = LightBackground,
    secondary = lightCardBack,
    tertiary = textLight,
    background = LightBackground,
    onPrimary = Black,
    onBackground = SheetLight

)

@Composable
fun CalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit

) {
    val isDark = isSystemInDarkTheme()
    val dark by remember {
        mutableStateOf(isDark)
    }
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val systemUiController = rememberSystemUiController()
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = if (dark) DarkBackground else LightBackground,
                darkIcons = false
            )
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
