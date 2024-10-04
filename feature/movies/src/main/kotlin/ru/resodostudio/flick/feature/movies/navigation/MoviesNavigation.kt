package ru.resodostudio.flick.feature.movies.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.resodostudio.flick.feature.movies.MoviesRoute

const val MOVIES_GRAPH_ROUTE_PATTERN = "movies_graph"
const val moviesRoute = "movies_route"

fun NavController.navigateToMoviesGraph(navOptions: NavOptions? = null) {
    this.navigate(MOVIES_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.moviesGraph(
    onMovieClick: (Int) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = MOVIES_GRAPH_ROUTE_PATTERN,
        startDestination = moviesRoute,
    ) {
        composable(route = moviesRoute) {
            MoviesRoute(onMovieClick = onMovieClick)
        }
        nestedGraphs()
    }
}