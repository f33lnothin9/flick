package ru.resodostudios.flick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Alignment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.resodostudios.flick.core.designsystem.component.Banner
import ru.resodostudios.flick.core.designsystem.component.NavDrawer
import ru.resodostudios.flick.core.designsystem.theme.FlickTheme
import ru.resodostudios.flick.navigation.NavHost

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            FlickTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)

                Surface {
                    Box(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        NavDrawer(navController = navController, drawerState = drawerState) {
                            NavHost(navController = navController, drawerState = drawerState)
                        }
                        Banner(id = R.string.banner_main)
                    }
                }
            }
        }
    }
}