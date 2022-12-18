@file:OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)

package compose.lets.calculator.alert

import android.content.Context.WINDOW_SERVICE
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import compose.lets.calculator.intro
import compose.lets.calculator.ui.theme.DarkBackground
import compose.lets.calculator.ui.theme.LightBackground

@Composable
fun NewFeatures(
    background: Boolean,
    navHostController: NavHostController
) {
    val painter = rememberImagePainter(
        data = "https://firebasestorage.googleapis.com/v0/b/" +
            "mirage-51bf3.appspot.com/o/months.jpg?alt=media&token=18b23f9b-6868-4de8-967b-" +
            "98a44213f80b",
        builder = {}
    )
    val painter2 = rememberImagePainter(
        data = "https://github.com/thekaailashsh" +
            "arma/Hiper/blob/master/app/src/main/res/drawable/add.png?raw=true",
        builder = {}
    )
    val painter3 = rememberImagePainter(
        data = "https://github.com/thekaailashsh" +
            "arma/Hiper/blob/master/app/src/main/res/drawable/chf.png?raw=true",
        builder = {}
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (background) DarkBackground else LightBackground)
    ) {
        val listOfIm = listOf(painter, painter2, painter3)
        Stories(
            list = listOfIm,
            background = background,
            navHostController = navHostController
        )
    }
}

@Composable
fun Stories(
    list: List<ImagePainter>,
    background: Boolean,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val windowManager = context.applicationContext.getSystemService(WINDOW_SERVICE) as WindowManager
    val display = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(display)
    val height = display.heightPixels
    val shimmerColor = mutableListOf<Color>()
    if (background) {
        shimmerColor.add(Color(0xFF4C5366).copy(alpha = 0.4f))
        shimmerColor.add(Color(0xFF4C5366).copy(alpha = 0.25f))
        shimmerColor.add(Color(0xFF4C5366).copy(alpha = 0.4f))
    } else {
        shimmerColor.add(Color(0xFFA09B9B).copy(alpha = 0.4f))
        shimmerColor.add(Color(0xFFA09B9B).copy(alpha = 0.25f))
        shimmerColor.add(Color(0xFFA09B9B).copy(alpha = 0.4f))
    }

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = height.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColor,
        start = Offset(x = 0f, y = 0f),
        end = Offset(x = translateAnimation.value, y = 0f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {}
    ) {
        NewUser(
            url = intro,
            speed = 2.0f,
            navHostController = navHostController,
            brush = brush
        )
    }
}
