package compose.lets.calculator.calci

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.lets.calculator.R
import compose.lets.calculator.nav.Screen
import compose.lets.calculator.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun Toggle(navHostController: NavHostController, background: Boolean, change: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var isDark by remember {
        mutableStateOf(true)
    }
    var curren by remember {
        mutableStateOf(true)
    }

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                coroutineScope.launch {
                    navHostController.navigate(Screen.Alert.route) {
                    }
                }
            },
            modifier = Modifier
                .padding(top = 11.dp, start = 12.dp)
                .size(50.dp)

        ) {
            val currenanim by rememberLottieComposition(
                spec = LottieCompositionSpec.Asset("new.json")
            )
            val progress by animateLottieCompositionAsState(currenanim)
            LottieAnimation(
                composition = currenanim,
                iterations = Int.MAX_VALUE,
                isPlaying = curren,
                contentScale = ContentScale.Crop,
                speed = 1.0f,
                modifier = Modifier
                    .size(200.dp)
            )
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier.padding(start = 10.dp, top = 7.dp),
            border = BorderStroke(color = lastColumn, width = 1.dp),
            colors = CardDefaults.cardColors(if (isDark) DarkBackground else LightBackground)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .background(if (background) darkCardBack else lightCardBack)
                    .fillMaxHeight(0.05f)
            ) {
                IconButton(
                    onClick = change,
                    enabled = background
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.light),
                        contentDescription = "light",
                        tint = if (isDark) Color.Unspecified else lastColumn
                    )
                }
                IconButton(
                    onClick = change,
                    enabled = !background
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.dark),
                        contentDescription = "dark",
                        tint = if (isDark) lastColumn else Color.Black
                    )
                }
            }
        }

        IconButton(
            onClick = {
                curren = !curren
                coroutineScope.launch {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Curren.route) {
                    }
                }
            },
            modifier = Modifier
                .size(70.dp)
                .padding(end = 10.dp, top = 7.dp)
        ) {
            val currenanim by rememberLottieComposition(
                spec = LottieCompositionSpec.Asset("ruppeee.json")
            )
            val progress by animateLottieCompositionAsState(currenanim)
            LottieAnimation(
                composition = currenanim,
                iterations = 1,
                isPlaying = curren,
                contentScale = ContentScale.Crop,
                speed = 1.0f,
                modifier = Modifier
                    .size(200.dp)
            )
            if (progress == 1.0f) curren = true
        }
    }
}
