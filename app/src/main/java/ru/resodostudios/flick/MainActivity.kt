package ru.resodostudios.flick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.resodostudios.flick.core.data.util.NetworkMonitor
import ru.resodostudios.flick.core.designsystem.theme.FlickTheme
import ru.resodostudios.flick.ui.FlickApp
import javax.inject.Inject

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            FlickTheme {
                FlickApp(
                    networkMonitor = networkMonitor
                )
            }
        }
    }
}