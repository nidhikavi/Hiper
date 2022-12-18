@file:OptIn(ExperimentalMaterialApi::class)

package compose.lets.calculator.calci

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
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
import compose.lets.calculator.differentScreens.WindowInfo
import compose.lets.calculator.differentScreens.rememberWindowInfo
import compose.lets.calculator.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Calculator(
    state: State,
    onAction: (Action) -> Unit,
    liveS: LiveState,
    navHostController: NavHostController,
    background: Boolean,
    change: () -> Unit,
    calViewModel: CalViewModel

) {
    val windowType = rememberWindowInfo()
    var width = 120.dp
    var customHeight = 0.32f
    if (windowType.screenHeightType is WindowInfo.WindowType.Compact) {
        customHeight = 0.19f
        width = 92.dp
    }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val liveHistory by remember {
        mutableStateOf(false)
    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            SheetContent(
                listState = listState,
                background = background,
                calViewModel = calViewModel,
                onAction = onAction
            )
        },
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(11.dp),
        sheetPeekHeight = if (liveHistory) 500.dp else 0.dp

    ) {
        Column(
            modifier = Modifier
                .background(color = if (background) DarkBackground else LightBackground)
                .fillMaxSize()
                .statusBarsPadding()

        ) {
            Column() {
                Toggle(
                    navHostController = navHostController,
                    background = background,
                    change = change
                )
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxHeight(customHeight)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                    ) {
                        Text(
                            text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .background(
                                    color = if (background) DarkBackground else LightBackground
                                )
                                .fillMaxWidth()
                                .padding(top = 15.dp)
                                .padding(end = 30.dp),
                            fontWeight = FontWeight.ExtraBold,
                            style = TextStyle(color = if (background) textDark else textLight),
                            fontSize = 48.sp
                        )
                        Text(
                            text = liveS.answer,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .background(
                                    color = if (background) DarkBackground else LightBackground
                                )
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .padding(end = 20.dp),
                            fontWeight = FontWeight.ExtraBold,
                            style = TextStyle(color = Color(0xFF919998)),
                            fontSize = 20.sp
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                    modifier = Modifier
                        .fillMaxHeight(),
                    elevation = CardDefaults.cardElevation(15.dp),
                    colors = CardDefaults.elevatedCardColors(
                        if (background) darkCardBack else lightCardBack
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Row(horizontalArrangement = Arrangement.Start) {
                            TextButton(
                                onClick = { onAction(Action.AC) },
                                shape = CircleShape,
                                colors = ButtonDefaults
                                    .buttonColors(
                                        containerColor =
                                        if (background) darkCardBack else lightCardBack
                                    ),
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        top = 18.dp,
                                        end = 15.dp,
                                        bottom = 15.dp
                                    )
                                    .size(68.dp)
                            ) {
                                Text(
                                    text = "AC",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 29.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Clear) },
                                shape = CircleShape,
                                colors = ButtonDefaults
                                    .buttonColors(
                                        containerColor = if (background) {
                                            darkCardBack
                                        } else lightCardBack
                                    ),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.delete),
                                    contentDescription = "Delete",
                                    tint = firstRow,
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .weight(1.3f)
                                        .fillMaxSize()
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Operation(Operation.Modulus)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (background) {
                                        darkCardBack
                                    } else lightCardBack
                                ),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Text(
                                    text = "%",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1,
                                    color = lastColumn
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Operation(Operation.Divide)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                    if (background) darkCardBack
                                    else lightCardBack
                                ),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 25.dp, end = 0.dp, start = 5.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.div),
                                    contentDescription = "Delete",
                                    tint = lastColumn,
                                    modifier = Modifier
                                        .aspectRatio(0.5f)
                                        .weight(1f)
                                        .fillMaxSize()
                                )
                            }
                        }
                        Row(horizontalArrangement = Arrangement.Start) {
                            TextButton(
                                onClick = { onAction(Action.Number(7)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (background) darkCardBack else lightCardBack
                                ),
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        top = 18.dp,
                                        end = 15.dp,
                                        bottom = 15.dp
                                    )
                                    .size(68.dp)
                            ) {
                                Text(
                                    text = "7",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Number(8)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Text(
                                    text = "8",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Number(9)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                    if (background) darkCardBack else lightCardBack
                                ),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Text(
                                    text = "9",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Operation(Operation.Multiply)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (background) darkCardBack else lightCardBack
                                ),
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.mul),
                                    contentDescription = "Delete",
                                    tint = lastColumn,
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .weight(1f)
                                        .fillMaxSize()
                                )
                            }
                        }
                        Row(horizontalArrangement = Arrangement.Start) {
                            TextButton(
                                onClick = { onAction(Action.Number(4)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (background) darkCardBack else lightCardBack
                                ),
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        top = 18.dp,
                                        end = 15.dp,
                                        bottom = 15.dp
                                    )
                                    .size(68.dp)
                            ) {
                                Text(
                                    text = "4",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Number(5)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Text(
                                    text = "5",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Number(6)) },
                                shape = CircleShape,
                                colors = ButtonDefaults
                                    .buttonColors(
                                        containerColor = if (background) {
                                            darkCardBack
                                        } else lightCardBack
                                    ),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Text(
                                    text = "6",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Operation(Operation.Subtract)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sub),
                                    contentDescription = "Delete",
                                    tint = lastColumn,
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .weight(1f)
                                        .fillMaxSize()
                                )
                            }
                        }
                        Row(horizontalArrangement = Arrangement.Start) {
                            TextButton(
                                onClick = { onAction(Action.Number(1)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        top = 18.dp,
                                        end = 15.dp,
                                        bottom = 15.dp
                                    )
                                    .size(68.dp)
                            ) {
                                Text(
                                    text = "1",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Number(2)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Text(
                                    text = "2",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Number(3)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Text(
                                    text = "3",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Operation(Operation.Add)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(top = 10.dp, start = 2.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.add),
                                    contentDescription = "Delete",
                                    tint = lastColumn,
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .weight(1f)
                                        .fillMaxSize()
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val dark = isSystemInDarkTheme()
                            if (dark) {
                                val composition3 by rememberLottieComposition(
                                    spec = LottieCompositionSpec.Asset("calcdark.json")
                                )
                                LottieAnimation(
                                    composition = composition3,
                                    iterations = Int.MAX_VALUE,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(start = 0.dp, top = 0.dp)
                                        .clickable {
                                            coroutineScope.launch {
                                                scaffoldState.bottomSheetState.expand()
                                            }
                                        }
                                )
                            } else {
                                val composition3 by rememberLottieComposition(
                                    spec = LottieCompositionSpec.Asset("calc.json")
                                )
                                LottieAnimation(
                                    composition = composition3,
                                    iterations = Int.MAX_VALUE,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clickable {
                                            coroutineScope.launch {
                                                scaffoldState.bottomSheetState.expand()
                                            }
                                        }
                                )
                            }

                            Button(
                                onClick = { onAction(Action.Number(0)) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 0.dp, start = 2.dp)
                            ) {
                                Text(
                                    text = "0",
                                    style = TextStyle(
                                        color = firstRow,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 2.dp)
                                        .aspectRatio(1.0f)
                                        .weight(1f),
                                    fontSize = 36.sp,
                                    maxLines = 1
                                )
                            }
                            Button(
                                onClick = { onAction(Action.Decimal) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 0.dp, start = 2.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.dot),
                                    contentDescription = "Delete",
                                    tint = lastColumn,
                                    modifier = Modifier
                                        .aspectRatio(0.5f)
                                        .weight(1f)
                                        .fillMaxSize()
                                )
                            }
                            Button(
                                onClick = {
                                    onAction(Action.Calculate)
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = if (background) darkCardBack else lightCardBack),
                                modifier = Modifier
                                    .size(width)
                                    .padding(top = 10.dp, start = 2.dp, bottom = 15.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.equal),
                                    contentDescription = "Delete",
                                    tint = lastColumn,
                                    modifier = Modifier
                                        .aspectRatio(0.5f)
                                        .weight(1f)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
