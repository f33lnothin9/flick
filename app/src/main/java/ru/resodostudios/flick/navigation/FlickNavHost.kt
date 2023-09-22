package ru.resodostudios.flick.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import ru.resodostudios.flick.feature.favorites.navigation.favoritesScreen
import ru.resodostudios.flick.feature.movie.navigation.movieScreen
import ru.resodostudios.flick.feature.movie.navigation.navigateToMovie
import ru.resodostudios.flick.feature.movies.navigation.MOVIES_GRAPH_ROUTE_PATTERN
import ru.resodostudios.flick.feature.movies.navigation.moviesGraph
import ru.resodostudios.flick.feature.people.navigation.peopleGraph
import ru.resodostudios.flick.feature.person.navigation.navigateToPerson
import ru.resodostudios.flick.feature.person.navigation.personScreen
import ru.resodostudios.flick.feature.search.navigation.searchScreen
import ru.resodostudios.flick.ui.FlickAppState

@Composable
fun FlickNavHost(
    appState: FlickAppState,
    startDestination: String = MOVIES_GRAPH_ROUTE_PATTERN,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
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
        favoritesScreen(
            onMovieClick = navController::navigateToMovie,
            onPersonClick = navController::navigateToPerson
        )
        searchScreen(
            onBackClick = navController::popBackStack,
            onMovieClick = { movieId ->
                navController.navigateToMovie(movieId)
            },
            onPersonClick = { personId ->
                navController.navigateToPerson(personId)
            }
        )
    }
}