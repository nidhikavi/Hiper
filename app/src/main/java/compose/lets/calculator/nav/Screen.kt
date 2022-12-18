package compose.lets.calculator.nav

sealed class Screen(val route: String) {
    object Calci : Screen("calci")
    object Curren : Screen("curren")
    object Boarding : Screen("boarding")
    object Alert : Screen("alert")
}
