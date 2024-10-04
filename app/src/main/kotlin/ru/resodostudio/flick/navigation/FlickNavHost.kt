package ru.resodostudio.flick.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import ru.resodostudio.flick.feature.home.navigation.HOME_GRAPH_ROUTE_PATTERN
import ru.resodostudio.flick.feature.home.navigation.homeGraph
import ru.resodostudio.flick.feature.movie.navigation.movieScreen
import ru.resodostudio.flick.feature.movie.navigation.navigateToMovie
import ru.resodostudio.flick.feature.movies.navigation.moviesGraph
import ru.resodostudio.flick.feature.people.navigation.peopleGraph
import ru.resodostudio.flick.feature.person.navigation.navigateToPerson
import ru.resodostudio.flick.feature.person.navigation.personScreen
import ru.resodostudio.flick.feature.settings.navigation.settingsScreen
import ru.resodostudio.flick.ui.FlickAppState

@Composable
fun FlickNavHost(
    appState: FlickAppState,
    startDestination: String = HOME_GRAPH_ROUTE_PATTERN,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeGraph(
            nestedGraphs = {

            }
        )
        moviesGraph(
            onMovieClick = { movieId ->
                navController.navigateToMovie(movieId)
            },
            nestedGraphs = {
                movieScreen(
                    onBackClick = navController::popBackStack,
                    onPersonClick = { personId ->
                        navController.navigateToPerson(personId)
                    }
                )
            }
        )
        peopleGraph(
            onPersonClick = { personId ->
                navController.navigateToPerson(personId)
            },
            nestedGraphs = {
                personScreen(
                    onBackClick = navController::popBackStack,
                    onMovieClick = { movieId ->
                        navController.navigateToMovie(movieId)
                    }
                )
            }
        )
        settingsScreen(
            onBackClick = navController::popBackStack
        )
    }
}