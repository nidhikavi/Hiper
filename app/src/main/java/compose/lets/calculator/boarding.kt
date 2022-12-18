package compose.lets.calculator

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import compose.lets.calculator.nav.Screen
import compose.lets.calculator.sharedpreferences.Board
import compose.lets.calculator.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Boarding(
    navHostController: NavHostController,
    background: Boolean,
) {
    val items = ArrayList<Obdata>()
    val pagerState = rememberPagerState(pageCount = 4)
    items.add(
        Obdata(
            image = R.drawable.one,
            title = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = if (background) White else Black,
                        fontSize = 26.sp
                    )
                ) {
                    append("Welcome To  ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF55C1D4),
                        fontSize = 35.sp,
                        fontFamily = title,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("Hiper ")
                }
            },
            description = "My First Jetpack Compose Application"
        )
    )
    items.add(
        Obdata(
            image = R.drawable.two,
            title = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = if (background) White else Black,
                        fontSize = 26.sp
                    )
                ) {
                    append("Future of  ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF55C1D4),
                        fontSize = 35.sp,
                        fontFamily = title,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("UI")
                }
            },
            description = "Bye Bye XML... All new Jetpack is Here... "
        )
    )
    items.add(
        Obdata(
            image = R.drawable.threee,
            title = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = if (background) White else Black,
                        fontSize = 26.sp
                    )
                ) {
                    append("Simple Minimal ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF55C1D4),
                        fontSize = 35.sp,
                        fontFamily = title,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("Calculator")
                }
            },
            description = "Less Start Simple \n My First Calculator Application "
        )
    )
    items.add(
        Obdata(
            image = R.drawable.four,
            title = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = if (background) White else Black,
                        fontSize = 26.sp
                    )
                ) {
                    append("Click ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF55C1D4),
                        fontSize = 35.sp,
                        fontFamily = title,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("Here ")
                }
            },
            description = "Lets Get Started..."
        )
    )
    Bgresource(
        item = items,
        pagerState = pagerState,
        navHostController = navHostController,
        background = background,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Bgresource(
    item: List<Obdata>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    background: Boolean,

) {
    val context = LocalContext.current
    val dataStore = Board(context)

    Column(
        modifier = modifier.background(
            color =
            if (background) DarkBackground else LightBackground
        )
    ) {
        val coroutineScope = rememberCoroutineScope()
        HorizontalPager(state = pagerState) {
            page ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxHeight()
                        .background(color = if (background) DarkBackground else LightBackground)
                ) {
                    Image(
                        painter = painterResource(id = item[page].image),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .fillMaxHeight(0.62f)
                            .fillMaxWidth(),
                        alignment = Alignment.TopCenter
                    )
                    Box(contentAlignment = Alignment.BottomCenter) {
                        Card(
                            shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp),
                            elevation = CardDefaults.cardElevation(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(2.5f),
                            colors = CardDefaults
                                .cardColors(if (background) darkCardBack else lightCardBack)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = item[page].title,
                                    fontFamily = FontFamily(Font(R.font.title)),
                                    fontSize = 30.sp,
                                    color = if (background) darkCardBack else lightCardBack
                                )
                                Text(
                                    text = item[page].description,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(vertical = 50.dp)
                                )
                            }
                        }
                        Box(
                            contentAlignment = Alignment.BottomStart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 50.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.padding(top = 5.dp, start = 15.dp)
                            ) {
                                repeat(item.size) {
                                    Indicator(isSelected = it == pagerState.currentPage)
                                }
                            }
                        }
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 15.dp)
                        ) {
                            if (page != (item.size - 1)) {
                                val px = (pagerState.currentPage + 1) / (pagerState.pageCount)
                                    .toFloat()
                                var animationplayed by remember {
                                    mutableStateOf(false)
                                }
                                val curPer = animateFloatAsState(
                                    targetValue = if (animationplayed) px else 0f,
                                    animationSpec = tween(
                                        durationMillis = 1000
                                    )
                                )
                                LaunchedEffect(key1 = true) {
                                    animationplayed = true
                                }
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.size(100.dp)
                                ) {
                                    Canvas(modifier = Modifier.size(100.dp)) {
                                        drawArc(
                                            color = Color(0xFF01FF73),
                                            startAngle = -90f,
                                            sweepAngle = (360 * curPer.value),
                                            useCenter = false,
                                            style = Stroke(
                                                width = 4f,
                                                cap = StrokeCap.Round
                                            )
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(
                                                    pagerState
                                                        .currentPage + 1
                                                )
                                            }
                                        },
                                        modifier = Modifier.size(70.dp)
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(CircleShape),
                                            painter = painterResource(id = R.drawable.nex),
                                            contentDescription = "next",
                                            tint = if (background) White else Black
                                        )
                                    }
                                }
                            } else {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.size(100.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                dataStore.saveBoard("truee")
                                            }
                                            coroutineScope.launch {
                                                navHostController.popBackStack()
                                                navHostController.navigate(
                                                    Screen.Calci.route
                                                )
                                            }
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.PlayArrow,
                                            modifier = Modifier
                                                .size(180.dp)
                                                .clip(CircleShape),
                                            contentDescription = "next",
                                            tint = if (background) White else Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = modifier.fillMaxHeight()
        ) {
            Card(
                colors = CardDefaults
                    .cardColors(if (background) DarkBackground else LightBackground),
                elevation = CardDefaults.cardElevation(15.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "built with",
                        fontFamily = builtwith,
                        color = if (background) White else Black,
                        fontSize = 20.sp
                    )
                    val composition3 by rememberLottieComposition(
                        spec = LottieCompositionSpec.Asset("heart.json")
                    )
                    LottieAnimation(
                        composition = composition3,
                        iterations = Int.MAX_VALUE,
                        modifier = Modifier
                            .size(80.dp)
                            .aspectRatio(0.9f)
                            .padding(top = 17.dp)
                            .offset(x = (-23).dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 25.dp else 10.dp)
    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) Color(0xFF55C1D4) else Color(0xFFF8D6A1)
            )
    )
}
