package compose.lets.calculator.calci

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import compose.lets.calculator.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.lets.calculator.nav.Screen
import compose.lets.calculator.ui.theme.lastColumn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun toggle(navHostController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isDark by remember {
        mutableStateOf(true)
    }
    var curren by remember {
        mutableStateOf(true)
    }


    var dark = isSystemInDarkTheme()
    var bright = !dark

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Row(verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth())
    {

        Card(shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier.padding(start = screenWidth / 2.6f),
            border = BorderStroke(color = lastColumn, width = 1.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxHeight(0.05f)
            ) {
                IconButton(onClick = {
                    Toast.makeText(context,
                        "Change System Theme to Change App Theme",
                        Toast.LENGTH_LONG).show()
                },
                    enabled = bright
                ) {
                    Icon(painter = painterResource(id = R.drawable.light),
                        contentDescription = "light",
                        tint = if (isDark) Color.Unspecified else lastColumn)
                }
                IconButton(onClick = {
                    Toast.makeText(context,
                        "Change System Theme to Change App Theme",
                        Toast.LENGTH_LONG).show()
                },
                    enabled = dark) {
                    Icon(painter = painterResource(id = R.drawable.dark),
                        contentDescription = "dark",
                        tint = if (isDark) lastColumn else Color.Black)

                }

            }
        }

        IconButton(onClick = { curren = !curren
                            coroutineScope.launch{
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Curren.route){
                                }
                            }
                             },
            modifier = Modifier
                .size(70.dp)
                .padding(start = 100.dp)) {

            val currenanim by rememberLottieComposition(
                spec = LottieCompositionSpec.Asset("ruppeee.json"))
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

