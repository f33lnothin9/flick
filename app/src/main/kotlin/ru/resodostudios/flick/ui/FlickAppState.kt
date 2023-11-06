package ru.resodostudios.flick.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.resodostudios.core.data.util.NetworkMonitor
import ru.resodostudios.flick.feature.home.navigation.homeRoute
import ru.resodostudios.flick.feature.home.navigation.navigateToHomeGraph
import ru.resodostudios.flick.feature.movies.navigation.moviesRoute
import ru.resodostudios.flick.feature.movies.navigation.navigateToMoviesGraph
import ru.resodostudios.flick.feature.people.navigation.navigateToPeople
import ru.resodostudios.flick.feature.people.navigation.peopleRoute
import ru.resodostudios.flick.feature.search.navigation.navigateToSearch
import ru.resodostudios.flick.feature.settings.navigation.navigateToSettings
import ru.resodostudios.flick.navigation.TopLevelDestination
import ru.resodostudios.flick.navigation.TopLevelDestination.*

@Composable
fun rememberFlickAppState(
    networkMonitor: NetworkMonitor,
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): FlickAppState {

    return remember(
        navController,
        coroutineScope,
        networkMonitor,
        windowSizeClass
    ) {
        FlickAppState(
            navController,
            coroutineScope,
            windowSizeClass,
            networkMonitor
        )
    }
}

class FlickAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeRoute -> HOME
            moviesRoute -> MOVIES
            peopleRoute -> PEOPLE
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            HOME -> navController.navigateToHomeGraph(topLevelNavOptions)
            MOVIES -> navController.navigateToMoviesGraph(topLevelNavOptions)
            TV_SHOWS -> TODO()
            PEOPLE -> navController.navigateToPeople(topLevelNavOptions)
        }
    }

    fun navigateToSearch() {
        navController.navigateToSearch()
    }

    fun navigateToSettings() {
        navController.navigateToSettings()
    }
}