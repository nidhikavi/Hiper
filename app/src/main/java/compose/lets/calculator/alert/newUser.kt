package compose.lets.calculator.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.lets.calculator.nav.Screen

@Composable
fun NewUser(
    url: String,
    speed: Float = 1.0f,
    navHostController: NavHostController,
    brush: Brush
) {
    val currr by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("ig.json")
    )
    val result = rememberLottieComposition(
        spec = LottieCompositionSpec.Asset(
            "introducing.json"
        )
    )
    val progress by animateLottieCompositionAsState(composition = currr)
    Box(modifier = Modifier.fillMaxSize()) {
        if (progress == 1.0f) {
            LaunchedEffect(Unit) {
                navHostController.popBackStack()
                navHostController.navigate(Screen.Calci.route)
            }
        }

        if (result.isLoading) {
            val currenanim by rememberLottieComposition(
                spec = LottieCompositionSpec.Asset("loading.json")
            )
            LottieAnimation(
                composition = currenanim,
                iterations = 10,
                contentScale = ContentScale.Fit,
                speed = 1.0f,
                modifier = Modifier.background(brush = brush)
            )
        } else if (result.isFailure) {
            val currenanim by rememberLottieComposition(
                spec = LottieCompositionSpec.Asset("error.json")
            )
            LottieAnimation(
                composition = currenanim,
                iterations = 5,
                contentScale = ContentScale.Fit,
                speed = 1.0f
            )
        } else {
            LottieAnimation(
                composition = currr,
                speed = speed,
                iterations = 1,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
