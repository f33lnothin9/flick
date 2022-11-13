package ru.resodostudios.movies.presentation.screens.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.resodostudios.movies.presentation.navigation.Screens
import ru.resodostudios.movies.presentation.screens.splash.components.Splash

@Composable
fun SplashScreen(navController: NavController) {
    var startAnimate by remember {
        mutableStateOf(false)
    }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimate) 1f else 0f,
        animationSpec = tween(durationMillis = 2500)
    )
    
    LaunchedEffect(key1 = true) {
        startAnimate = true
        delay(3000)
        navController.navigate(Screens.Main.route)
    }

    Splash()
}