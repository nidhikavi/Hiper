package compose.lets.calculator.calci

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.lets.calculator.*
import compose.lets.calculator.R
import compose.lets.calculator.State
import compose.lets.calculator.ui.theme.firstRow
import compose.lets.calculator.ui.theme.lastColumn
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Calculator(state: State,
               onAction: (Action) ->  Unit,
               liveS: LiveState,
               itemC: Item,
               result: History,
               hisArray: HisArray,
               navHostController: NavHostController
){

    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)) {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier.fillMaxHeight()) {
            toggle(navHostController = navHostController)
            Box(contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxHeight(0.34f)) {
                Column {

                    Row(horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).padding(end = 15.dp)){
                        LazyColumn(state = listState, modifier = Modifier.fillMaxWidth().padding(start = 15.dp)) {
                            items(hisArray.hisArray.size) {
                                println("item count = ${itemC.count} \n it = $it")
                                    Text(
                                        text = hisArray.hisArray[it],
                                        textAlign = TextAlign.End,
                                        modifier = Modifier
                                            .background(color = MaterialTheme.colorScheme.primary)
                                            .padding(vertical = 3.dp)
                                            .padding(end = 20.dp),
                                        fontWeight = FontWeight.ExtraBold,
                                        style = TextStyle(color = Color(0xFF009688)),
                                        fontSize = 20.sp
                                    )
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index = hisArray.hisArray.size)
                                }
                            }

                        }
                    }


                    Text(
                        text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .padding(end = 30.dp)
                            ,fontWeight = FontWeight.ExtraBold,
                        style = TextStyle(color = MaterialTheme.colorScheme.tertiary),
                        fontSize = 48.sp
                    )
                    Text(
                        text = liveS.answer,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .padding(end = 20.dp)
                        ,fontWeight = FontWeight.ExtraBold,
                        style = TextStyle(color = Color(0xFF919998)),
                        fontSize = 20.sp
                    )
                }
            }
            Card(shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                elevation = CardDefaults.cardElevation(15.dp),
            colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.secondary)) {
                Column {
                    Row(horizontalArrangement = Arrangement.Start) {
                        TextButton(onClick = { onAction(Action.AC) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .padding(start = 20.dp, top = 18.dp, end = 15.dp, bottom = 15.dp)
                                .size(68.dp)) {
                            Text(text = "AC",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =26.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Clear) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 10.dp, start = 2.dp)) {
                            Icon(painter = painterResource(id = R.drawable.delete), contentDescription = "Delete",
                            tint = firstRow,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1.3f)
                                .fillMaxSize())
                        }
                        Button(onClick = { onAction(Action.Operation(Operation.Modulus)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 10.dp, start = 2.dp)) {
                            Text(text = "%",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center,),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1,
                                color = lastColumn
                            )
                        }
                        Button(onClick = { onAction(Action.Operation(Operation.Divide)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 25.dp, end = 0.dp, start = 5.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.div), contentDescription = "Delete",
                                tint = lastColumn,
                                modifier = Modifier
                                    .aspectRatio(0.5f)
                                    .weight(1.3f)
                                    .fillMaxSize()
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.Start) {
                        TextButton(onClick = { onAction(Action.Number(7))},
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .padding(start = 20.dp, top = 18.dp, end = 15.dp, bottom = 15.dp)
                                .size(68.dp)) {
                            Text(text = "7",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Number(8)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 10.dp, start = 2.dp)) {
                            Text(text = "8",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Number(9)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 10.dp, start = 2.dp)) {
                            Text(text = "9",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Operation(Operation.Multiply)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(100.dp)
                                .padding(top = 10.dp, start = 2.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.mul), contentDescription = "Delete",
                                tint = lastColumn,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1.3f)
                                    .fillMaxSize())
                        }
                    }
                    Row(horizontalArrangement = Arrangement.Start) {
                        TextButton(onClick = { onAction(Action.Number(4)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .padding(start = 20.dp, top = 18.dp, end = 15.dp, bottom = 15.dp)
                                .size(68.dp)) {
                            Text(text = "4",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Number(5)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 10.dp, start = 2.dp)) {
                            Text(text = "5",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Number(6)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 10.dp, start = 2.dp)) {
                            Text(text = "6",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Operation(Operation.Subtract)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(100.dp)
                                .padding(top = 10.dp, start = 2.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.sub), contentDescription = "Delete",
                                tint = lastColumn,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1.3f)
                                    .fillMaxSize())
                        }
                    }
                    Row(horizontalArrangement = Arrangement.Start) {
                        TextButton(onClick = { onAction(Action.Number(1)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .padding(start = 20.dp, top = 18.dp, end = 15.dp, bottom = 15.dp)
                                .size(68.dp)) {
                            Text(text = "1",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Number(2)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 10.dp, start = 2.dp)) {
                            Text(text = "2",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Number(3)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 10.dp, start = 2.dp)) {
                            Text(text = "3",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Operation(Operation.Add)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(100.dp)
                                .padding(top = 10.dp, start = 2.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.add), contentDescription = "Delete",
                                tint = lastColumn,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1.3f)
                                    .fillMaxSize())
                        }
                    }
                    Row(horizontalArrangement = Arrangement.Start) {
                        val dark = isSystemInDarkTheme()
                        if (dark){
                            val composition3 by rememberLottieComposition(
                                spec = LottieCompositionSpec.Asset("calcdark.json"))
                            LottieAnimation(
                                composition = composition3,
                                iterations = 10,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(start = 0.dp, top = 0.dp),
                            )
                        }
                        else {
                            val composition3 by rememberLottieComposition(
                                spec = LottieCompositionSpec.Asset("calc.json")
                            )
                            LottieAnimation(
                                composition = composition3,
                                iterations = 10,
                                modifier = Modifier.size(100.dp),
                            )
                        }
                        Button(onClick = { onAction(Action.Number(0)) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 0.dp, start = 2.dp)) {
                            Text(text = "0",
                                style = TextStyle(color = firstRow, textAlign = TextAlign.Center),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 2.dp)
                                    .aspectRatio(1.0f)
                                    .weight(1.5f),
                                fontSize =36.sp,
                                maxLines = 1
                            )
                        }
                        Button(onClick = { onAction(Action.Decimal) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(90.dp)
                                .padding(top = 0.dp, start = 2.dp)) {
                            Icon(painter = painterResource(id = R.drawable.dot), contentDescription = "Delete",
                                tint = lastColumn,
                                modifier = Modifier
                                    .aspectRatio(0.5f)
                                    .weight(1.3f)
                                    .fillMaxSize())
                        }
                        Button(onClick = { onAction(Action.Calculate)
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(120.dp)
                                .padding(top = 10.dp, start = 2.dp, bottom = 15.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.equal), contentDescription = "Delete",
                                tint = lastColumn,
                                modifier = Modifier
                                    .aspectRatio(0.5f)
                                    .weight(1.3f)
                                    .fillMaxSize())
                        }
                    }

                }
            }
        }

    }
}











