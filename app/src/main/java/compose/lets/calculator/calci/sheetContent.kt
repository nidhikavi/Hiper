package compose.lets.calculator.calci

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.lets.calculator.R
import compose.lets.calculator.ui.theme.*
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@Composable
fun SheetContent(
    listState: LazyListState,
    background: Boolean,
    calViewModel: CalViewModel,
    onAction: (Action) -> Unit
) {
    val completeHistory by calViewModel.completeHistory.collectAsState(initial = listOf())
    val coroutineScope = rememberCoroutineScope()
    var curren by remember {
        mutableStateOf(true)
    }
    if (completeHistory.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (background) DarkBackground else LightBackground),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "History of Calculations ",
                    color = if (background) textDark else textLight,
                    fontSize = 35.sp,
                    fontFamily = builtwith,
                    modifier = Modifier.padding(start = 15.dp)
                )
                val currenanim by rememberLottieComposition(
                    spec = LottieCompositionSpec.Asset("delete.json")
                )
                val progress by animateLottieCompositionAsState(currenanim)
                LottieAnimation(
                    composition = currenanim,
                    iterations = Int.MAX_VALUE,
                    isPlaying = curren,
                    contentScale = ContentScale.Crop,
                    speed = 0.35f,
                    modifier = Modifier
                        .size(65.dp)
                        .clickable {
                            coroutineScope.launch {
                                calViewModel.deleteHistory()
                            }
                        }
                )
                if (progress == 1.0f) curren = true
            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.34f)
                    .background(color = if (background) DarkBackground else LightBackground)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp)
                ) {
                    items(completeHistory.size) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(
                                    rememberScrollState()
                                )
                                .clickable {
                                    onAction(Action.AC)
                                    onAction(
                                        Action.Number(
                                            completeHistory[it].result
                                                .toFloat()
                                                .roundToInt()
                                        )
                                    )
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = completeHistory[it].operation,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .background(
                                        color = if (background) DarkBackground else LightBackground
                                    )
                                    .padding(vertical = 3.dp)
                                    .padding(end = 20.dp),
                                fontWeight = FontWeight.ExtraBold,
                                style = TextStyle(color = Color(0xFF009688)),
                                fontSize = 20.sp
                            )
                            Text(
                                text = completeHistory[it].result,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .background(
                                        color = if (background) DarkBackground else LightBackground
                                    )
                                    .padding(vertical = 3.dp)
                                    .padding(end = 20.dp),
                                fontWeight = FontWeight.ExtraBold,
                                style = TextStyle(color = Color(0xFF009688)),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.44f)
                .background(color = if (background) DarkBackground else LightBackground)
        ) {
            Image(
                painter = painterResource(id = R.drawable.history),
                contentDescription = "history",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
                    .background(color = if (background) DarkBackground else LightBackground),
                alignment = Alignment.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (background) DarkBackground else LightBackground),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Introducing Live History !!",
                    color = lastColumn,
                    fontSize = 25.sp,
                    fontFamily = title,
                    modifier = Modifier.padding(start = 15.dp, bottom = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (background) DarkBackground else LightBackground),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "1. Click on the Result to use it",
                    color = firstRow,
                    fontSize = 16.sp,
                    fontFamily = curfont,
                    modifier = Modifier.padding(15.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (background) DarkBackground else LightBackground),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "2. Click on the Dustbin to delete everything",
                    color = firstRow,
                    fontSize = 16.sp,
                    fontFamily = curfont,
                    modifier = Modifier.padding(15.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (background) DarkBackground else LightBackground),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "3.Once Deleted cannot be retrieved again !!",
                    color = firstRow,
                    fontSize = 16.sp,
                    fontFamily = curfont,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}
