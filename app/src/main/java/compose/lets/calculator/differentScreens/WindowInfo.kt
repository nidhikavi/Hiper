package compose.lets.calculator.differentScreens

import androidx.compose.ui.unit.Dp

data class WindowInfo(
    val screenWidthType: WindowType,
    val screenHeightType: WindowType,
    val screenHeight: Dp,
    val screenWidth: Dp
) {
    sealed class WindowType {
        object Compact : WindowType()
        object Medium : WindowType()
        object Expanded : WindowType()
    }
}
