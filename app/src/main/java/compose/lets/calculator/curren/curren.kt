package compose.lets.calculator.curren


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.lets.calculator.R
import compose.lets.calculator.curren.data.Constants
import compose.lets.calculator.curren.data.NetworkResult
import compose.lets.calculator.curren.livenetwork.ConnectionState
import compose.lets.calculator.curren.livenetwork.currentConnectivityState
import compose.lets.calculator.curren.livenetwork.observeConnectivityAsFlow
import compose.lets.calculator.curren.viewmodel.currencyViewModel
import compose.lets.calculator.ui.theme.lastColumn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalCoroutinesApi::class)
@Composable
fun Currency(navHostController: NavHostController, mainViewModel:currencyViewModel) {
    val fromCurrencyCode = remember { mutableStateOf("USD") }
    val fromCurrencyIcon = remember { mutableStateOf(R.drawable.usd) }
    val toCurrencyCode = remember { mutableStateOf("INR") }
    val toCurrencyIcon = remember { mutableStateOf(R.drawable.ruppee) }
    val amountValue = remember { mutableStateOf("") }
    val context = LocalContext.current

    val convertedAmount = remember { mutableStateOf("") }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    var show by remember {
        mutableStateOf(false)
    }






    var isFromSelected = true


    BottomSheetScaffold(
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),

                ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(Constants.CURRENCY_CODES_LIST) { item ->
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (isFromSelected) {
                                        fromCurrencyCode.value = item.currencyCode
                                        fromCurrencyIcon.value = item.icon
                                    } else {
                                        toCurrencyCode.value = item.currencyCode
                                        toCurrencyIcon.value = item.icon
                                    }
                                    scope.launch { scaffoldState.bottomSheetState.collapse() }
                                }) {


                            Text(
                                text = "${item.currencyCode}\t ${item.countryName}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                            )
                            Icon(painter = painterResource(id = item.icon), contentDescription = "", tint = lastColumn,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 8.dp))

                        }
                    }
                }
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetElevation = 6.dp,
        sheetBackgroundColor = MaterialTheme.colorScheme.onBackground,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {


            Column(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)) {

                currenTop(navHostController = navHostController)
                if (isConnected) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ) {
                    Box(contentAlignment = Alignment.TopCenter,
                        modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "$ Live Currency $",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                lineHeight = 30.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = "From", color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(modifier = Modifier.padding(3.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            isFromSelected = true
                            scope.launch { scaffoldState.bottomSheetState.expand() }

                        }
                        .border(1.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            RoundedCornerShape(5.dp)),

                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()) {

                            Text(text = fromCurrencyCode.value,
                                modifier = Modifier.padding(10.dp),
                                color = MaterialTheme.colorScheme.onPrimary)

                            Icon(painter = painterResource(id = fromCurrencyIcon.value),
                                contentDescription = fromCurrencyCode.value,
                                tint = lastColumn,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(end = 8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(text = "To", color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(modifier = Modifier.padding(3.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            isFromSelected = false
                            scope.launch { scaffoldState.bottomSheetState.expand() }
                        }
                        .border(1.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            RoundedCornerShape(5.dp)),

                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()) {

                            Text(text = toCurrencyCode.value,
                                modifier = Modifier.padding(10.dp),
                                color = MaterialTheme.colorScheme.onPrimary)

                            Icon(painter = painterResource(id = toCurrencyIcon.value),
                                contentDescription = toCurrencyCode.value,
                                tint = lastColumn,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(end = 8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Amount", color = MaterialTheme.colorScheme.onPrimary)
                        Text(text = fromCurrencyCode.value,
                            color = MaterialTheme.colorScheme.onPrimary)
                    }
                    Spacer(modifier = Modifier.padding(3.dp))
                    OutlinedTextField(
                        value = amountValue.value,
                        onValueChange = { amountValue.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Icon(painter = painterResource(id = fromCurrencyIcon.value),
                                    contentDescription = fromCurrencyCode.value,
                                    tint = lastColumn,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(end = 0.dp))
                                Text("0.00",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(start = 7.dp))
                                Box(contentAlignment = Alignment.TopEnd,
                                    modifier = Modifier.fillMaxWidth()) {
                                    Icon(painter = painterResource(id = toCurrencyIcon.value),
                                        contentDescription = toCurrencyCode.value,
                                        tint = lastColumn,
                                        modifier = Modifier
                                            .size(25.dp)
                                            .padding(end = 0.dp))
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = fromCurrencyIcon.value),
                            contentDescription = fromCurrencyCode.value,
                            tint = lastColumn,
                            modifier = Modifier
                                .size(70.dp)
                                .padding(end = 20.dp)
                        )
                        val exchange by rememberLottieComposition(
                            spec = LottieCompositionSpec.Asset("exchange.json"))
                        LottieAnimation(
                            composition = exchange,
                            iterations = Int.MAX_VALUE,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(start = 0.dp, top = 0.dp))
                        Icon(
                            painter = painterResource(id = toCurrencyIcon.value),
                            contentDescription = toCurrencyCode.value,
                            tint = lastColumn,
                            modifier = Modifier
                                .size(70.dp)
                                .padding(start = 20.dp)
                        )
                    }



                    Spacer(modifier = Modifier.padding(20.dp))

                    Button(
                        onClick = {
                            mainViewModel.getExchangeRates(to = toCurrencyCode.value,
                                from = fromCurrencyCode.value,
                                amount = amountValue.value)
                            Log.d("Test", mainViewModel.exchangeRatesResponse.value.toString())
                            mainViewModel.exchangeRatesResponse.observe(
                                context as LifecycleOwner
                            ) { response ->
                                Log.d("Test", response.data.toString())
                                when (response) {
                                    is NetworkResult.Success<*> -> {
                                        response.data?.let {
                                            if (amountValue.value.isEmpty()) {
                                                amountValue.value = "1.00"
                                            }

                                            convertedAmount.value =
                                                it.result.toString()
                                            show = true


                                        }
                                    }
                                    is NetworkResult.Error<*> -> {
                                        Toast.makeText(context,
                                            response.message,
                                            Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    is NetworkResult.Loading<*> -> {
                                        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }

                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(width = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),

                        ) {
                        Text("CONVERT",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onPrimary)
                    }

                    Spacer(modifier = Modifier.padding(30.dp))


                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (show) {
                            Text(
                                text = "${amountValue.value} ${fromCurrencyCode.value} = ${convertedAmount.value} ${toCurrencyCode.value}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 20.sp
                            )

                        }
                    }


                }
            }else{
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        val dark = isSystemInDarkTheme()
                        if (dark) {
                            val composition3 by rememberLottieComposition(
                                spec = LottieCompositionSpec.Asset("internetdark.json"))
                            LottieAnimation(
                                composition = composition3,
                                iterations = Int.MAX_VALUE,
                            )
                        } else {
                            val composition3 by rememberLottieComposition(
                                spec = LottieCompositionSpec.Asset("internetlight.json"))
                            LottieAnimation(
                                composition = composition3,
                                iterations = Int.MAX_VALUE,
                            )
                        }
                        Box(contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.fillMaxWidth()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()) {
                                val composition4 by rememberLottieComposition(
                                    spec = LottieCompositionSpec.Asset("load.json"))
                                LottieAnimation(
                                    composition = composition4,
                                    iterations = Int.MAX_VALUE,
                                    modifier = Modifier.size(100.dp)
                                )
                                Text(
                                    text = "Umm!! it Seems you lost internet !! Reconnect",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                        }

                    }
                }
        }

    }

}
@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}


