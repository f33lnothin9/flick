package ru.resodostudios.flick.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import ru.resodostudios.flick.feature.favorites.navigation.favoritesScreen
import ru.resodostudios.flick.feature.movie.navigation.movieScreen
import ru.resodostudios.flick.feature.movie.navigation.navigateToMovie
import ru.resodostudios.flick.feature.movies.navigation.MOVIES_GRAPH_ROUTE_PATTERN
import ru.resodostudios.flick.feature.movies.navigation.moviesGraph
import ru.resodostudios.flick.feature.people.navigation.peopleScreen
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
                    onBackClick = navController::popBackStack
                )
            }
        )
        peopleScreen()
        favoritesScreen()
        searchScreen(
            onBackClick = navController::popBackStack,
            onMovieClick = { },
            onPersonClick = { }
        )
    }
}