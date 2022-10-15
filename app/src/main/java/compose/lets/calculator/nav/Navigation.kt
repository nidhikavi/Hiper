package compose.lets.calculator.nav

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import compose.lets.calculator.Boarding
import compose.lets.calculator.calci.CalViewModel
import compose.lets.calculator.calci.Calculator
import compose.lets.calculator.curren.Currency
import compose.lets.calculator.curren.viewmodel.currencyViewModel
import compose.lets.calculator.sharedpreferences.Board

@Composable
fun Navigation(
    navController: NavHostController,
    mainviewmodel: currencyViewModel,
    start:String
) {


    NavHost(
        navController = navController,
        startDestination = start
    ){
        composable(route = Screen.Calci.route){
            val viewModel = viewModel<CalViewModel>()
            val state = viewModel.state
            val ls = viewModel.liveState
            val iS = viewModel.itemCount
            val rs = viewModel.history
            val hs = viewModel.myArray
            Calculator(state= state,
                onAction = viewModel::myAction,
                liveS = ls,
                itemC = iS,
                result = rs,
                hisArray = hs,
                navHostController = navController
            )

        }
        composable(route = Screen.Curren.route){

            Currency(navHostController = navController, mainViewModel = mainviewmodel)
        }
        composable(route = Screen.Boarding.route){

            Boarding(navHostController = navController)
        }
    }
}