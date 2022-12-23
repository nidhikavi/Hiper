package compose.lets.calculator.nav

import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import compose.lets.calculator.Boarding
import compose.lets.calculator.alert.NewFeatures
import compose.lets.calculator.calci.CalViewModel
import compose.lets.calculator.calci.Calculator
import compose.lets.calculator.curren.Currency
import compose.lets.calculator.curren.viewmodel.currencyViewModel
import compose.lets.calculator.ui.theme.DarkBackground
import compose.lets.calculator.ui.theme.LightBackground
import kotlinx.coroutines.launch

@Composable
fun Navigation(
    navController: NavHostController,
    mainviewmodel: currencyViewModel,
    start: String
) {
    val isDark = isSystemInDarkTheme()
    var dark by remember {
        mutableStateOf(isDark)
    }
    val systemUiController = rememberSystemUiController()
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = start
    ) {
        composable(route = Screen.Calci.route) {
            val viewModel = viewModel<CalViewModel>()
            val state = viewModel.state
            val ls = viewModel.liveState
            val iS = viewModel.itemCount
            val rs = viewModel.history
            val hs = viewModel.myArray
            Calculator(
                state = state,
                onAction = viewModel::myAction,
                liveS = ls,
                navHostController = navController,
                background = dark,
                change = {
                    dark = !dark
                    coroutineScope.launch {
                        systemUiController.setSystemBarsColor(
                            color = if (dark) DarkBackground else LightBackground,
                            darkIcons = false
                        )
                    }
                },
                calViewModel = viewModel
            )
        }
        composable(route = Screen.Curren.route) {
            Currency(
                navHostController = navController,
                mainViewModel = mainviewmodel,
                background = dark,
                change = {
                    dark = !dark
                    coroutineScope.launch {
                        systemUiController.setSystemBarsColor(
                            color = if (dark) DarkBackground else LightBackground,
                            darkIcons = false
                        )
                    }
                }
            )
        }
        composable(route = Screen.Boarding.route) {
            Boarding(
                navHostController = navController,
                background = dark
            )
        }
        composable(
            route = Screen.Alert.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://thekaailashsharma.com"
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            NewFeatures(
                background = dark,
                navHostController = navController
            )
        }
    }
}
