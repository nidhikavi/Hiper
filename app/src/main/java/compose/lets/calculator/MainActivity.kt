package compose.lets.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import compose.lets.calculator.calci.CalViewModel
import compose.lets.calculator.calci.Calculator
import compose.lets.calculator.curren.viewmodel.currencyViewModel
import compose.lets.calculator.nav.Navigation
import compose.lets.calculator.sharedpreferences.Board
import compose.lets.calculator.ui.theme.CalculatorTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var splashViewModel = SplashViewModel(Board(LocalContext.current))
            installSplashScreen().setKeepOnScreenCondition{
                !splashViewModel.isLoading.value
            }
            CalculatorTheme {
                val navController = rememberNavController()
                val screen by splashViewModel.startDestination

                var mainViewModel: currencyViewModel = ViewModelProvider(this)[currencyViewModel::class.java]
                Navigation(navController = navController,mainViewModel, screen)
            }
        }
    }
}



