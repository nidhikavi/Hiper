package compose.lets.calculator.curren.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import compose.lets.calculator.curren.data.NetworkResult
import compose.lets.calculator.curren.data.dattares
import compose.lets.calculator.curren.reposirtory.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class currencyViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var exchangeRatesResponse: MutableLiveData<NetworkResult<dattares>> = MutableLiveData()

    fun getExchangeRates(to:String, from:String, amount: String) {
        viewModelScope.launch {
            getExchangeRatesSafeCall(to, from, amount)
        }
    }

    private suspend fun getExchangeRatesSafeCall(to:String, from:String, amount: String) {
        if (checkInternetConnection()) {

                val response = repository.remote.getExchangeRates(to, from, amount)
                exchangeRatesResponse.value = handleExchangeRatesResponse(response)
        } else {
            exchangeRatesResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun handleExchangeRatesResponse(response: Response<dattares>): NetworkResult<dattares>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error(message = "Time Out")
            }
            response.isSuccessful -> {
                val exchangeResponse = response.body()
                NetworkResult.Success(data = exchangeResponse!!)
            }
            else -> {
                NetworkResult.Error(message = "Could Not Fetch Results")
            }
        }
    }

     fun checkInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }


}