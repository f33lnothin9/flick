package ru.resodostudios.flick.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.resodostudios.flick.feature.favorites.navigation.favoritesScreen
import ru.resodostudios.flick.feature.movie.navigation.movieScreen
import ru.resodostudios.flick.feature.movie.navigation.navigateToMovie
import ru.resodostudios.flick.feature.movie.presentation.MovieViewModel
import ru.resodostudios.flick.feature.movies.navigation.MOVIES_GRAPH_ROUTE_PATTERN
import ru.resodostudios.flick.feature.movies.navigation.moviesGraph
import ru.resodostudios.flick.feature.people.navigation.peopleScreen
import ru.resodostudios.flick.feature.settings.presentation.SettingsScreen
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
    }
}

@ExperimentalMaterial3Api
@Composable
fun NavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Movies.route
    ) {
//        composable(route = Screens.Movies.route) {
//            val viewModel: MoviesViewModel = hiltViewModel()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//
//            MoviesScreen(
//                state = state,
//                onRetry = { viewModel.getMovies() },
//                onEvent = viewModel::onEvent
//            )
//        }

        composable(
            route = Screens.Movie.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            val id = remember {
                it.arguments?.getInt("id")
            }
            val viewModel: MovieViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            id?.let {
                viewModel.getMovie(it)
                viewModel.getCast(it)
                viewModel.getCrew(it)
            }

//            MovieScreen(
//                state = state,
//                onEvent = viewModel::onEvent,
//                onRetry = {
//                    id?.let {
//                        viewModel.getMovie(it)
//                        viewModel.getCast(it)
//                        viewModel.getCrew(it)
//                    }
//                }
//            )
        }

        composable(route = Screens.People.route) { }

        composable(route = Screens.Favorites.route) {
        }

        composable(route = Screens.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}