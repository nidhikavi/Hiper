package compose.lets.calculator


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import compose.lets.calculator.nav.Screen
import compose.lets.calculator.sharedpreferences.Board
import kotlinx.coroutines.launch
//
//
class SplashViewModel(
    private val repository: Board
) :ViewModel() {
    private val _isloading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isloading
    private val _startDestination : MutableState<String> = mutableStateOf(Screen.Calci.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.getBoard.collect{ completed ->
                if (completed == "true"){
                    _startDestination.value = Screen.Calci.route
                }
                else{
                    _startDestination.value = Screen.Boarding.route
                }

            }
            _isloading.value = false
        }
    }
}