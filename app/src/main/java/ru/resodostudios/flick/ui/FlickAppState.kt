package ru.resodostudios.flick.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import ru.resodostudios.flick.feature.favorites.navigation.favoritesNavigationRoute
import ru.resodostudios.flick.feature.favorites.navigation.navigateToFavorites
import ru.resodostudios.flick.feature.movies.navigation.moviesRoute
import ru.resodostudios.flick.feature.movies.navigation.navigateToMoviesGraph
import ru.resodostudios.flick.feature.people.navigation.navigateToPeople
import ru.resodostudios.flick.feature.people.navigation.peopleNavigationRoute
import ru.resodostudios.flick.navigation.TopLevelDestination
import ru.resodostudios.flick.navigation.TopLevelDestination.FAVORITES
import ru.resodostudios.flick.navigation.TopLevelDestination.MOVIES
import ru.resodostudios.flick.navigation.TopLevelDestination.PEOPLE

@Composable
fun rememberFlickAppState(
    navController: NavHostController = rememberNavController()
): FlickAppState {

    return remember(
        navController
    ) {
        FlickAppState(
            navController
        )
    }
}

class FlickAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            moviesRoute -> MOVIES
            peopleNavigationRoute -> PEOPLE
            favoritesNavigationRoute -> FAVORITES
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            MOVIES -> navController.navigateToMoviesGraph(topLevelNavOptions)
            PEOPLE -> navController.navigateToPeople(topLevelNavOptions)
            FAVORITES -> navController.navigateToFavorites(topLevelNavOptions)
        }
    }
}