package compose.lets.calculator

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import compose.lets.calculator.curren.viewmodel.currencyViewModel
import compose.lets.calculator.nav.Navigation
import compose.lets.calculator.sharedpreferences.Board
import compose.lets.calculator.ui.theme.CalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

var isForced = false
var darkkk = Color(0xFF22252d)
val map: MutableMap<String, Any> = mutableMapOf()
var intro = "https://firebasestorage.googleapis.com/v0/b/mirage-51bf3.appspot.com/o/introFull.json?" +
    "alt=media&token=8802ec12-f4a7-441f-a182-3a22e17a8e0d"
var latestVersion = "3"
var darkBackground = "0xFF22252d"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    fun hideSystemUI() {
        actionBar?.hide()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        map["intro"] = intro
        map["latestVersion"] = latestVersion
        map["darkBackground"] = darkBackground
        remoteConfig.setDefaultsAsync(map)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (remoteConfig.getLong("latestVersion") > BuildConfig.VERSION_CODE) {
                        println("Yes Update")
                        isForced = true
                    }
                    intro = remoteConfig.getString("intro")
                }
            }
        AppCenter.start(application, "d23b6264-83a1-4900-8826-acef27894f64", Analytics::class.java, Crashes::class.java)

        setContent {
            var splashViewModel = SplashViewModel(Board(LocalContext.current))
            installSplashScreen().setKeepOnScreenCondition {
                !splashViewModel.isLoading.value
            }
            println("Calendar = ${Calendar.MONTH}")

            CalculatorTheme() {
                println("packageName = $packageName")
                val navController = rememberNavController()
                val screen by splashViewModel.startDestination

                var mainViewModel: currencyViewModel =
                    ViewModelProvider(this)[currencyViewModel::class.java]
                Navigation(
                    navController = navController,
                    mainviewmodel = mainViewModel,
                    start = screen
                )
            }
        }
        hideSystemUI()
    }
}
