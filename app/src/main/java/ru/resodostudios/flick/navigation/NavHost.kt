package ru.resodostudios.flick.navigation

import androidx.compose.material3.DrawerState
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
import ru.resodostudios.flick.feature.favorites.presentation.FavoritesScreen
import ru.resodostudios.flick.feature.favorites.presentation.FavoritesViewModel
import ru.resodostudios.flick.feature.movie.presentation.MovieScreen
import ru.resodostudios.flick.feature.movie.presentation.MovieViewModel
import ru.resodostudios.flick.feature.movies.presentation.MoviesScreen
import ru.resodostudios.flick.feature.movies.presentation.MoviesViewModel
import ru.resodostudios.flick.feature.people.presentation.PeopleScreen
import ru.resodostudios.flick.feature.people.presentation.PeopleViewModel
import ru.resodostudios.flick.feature.settings.presentation.SettingsScreen

@ExperimentalMaterial3Api
@Composable
fun NavHost(
    navController: NavHostController,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Movies.route
    ) {
        composable(route = Screens.Movies.route) {
            val viewModel: MoviesViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            MoviesScreen(
                navController = navController,
                drawerState = drawerState,
                state = state,
                onRetry = { viewModel.getMovies() },
                onEvent = viewModel::onEvent
            )
        }

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

            MovieScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent,
                onRetry = {
                    id?.let {
                        viewModel.getMovie(it)
                        viewModel.getCast(it)
                        viewModel.getCrew(it)
                    }
                }
            )
        }

        composable(route = Screens.People.route) {
            val viewModel: PeopleViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            PeopleScreen(
                state = state,
                onRetry = { viewModel.getPeople() },
                drawerState = drawerState,
                onEvent = viewModel::onEvent
            )
        }

        composable(route = Screens.Favorites.route) {
            val viewModel: FavoritesViewModel = hiltViewModel()
            val viewModelMovie: MovieViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            FavoritesScreen(
                state = state,
                navController = navController,
                drawerState = drawerState,
                onEvent = viewModelMovie::onEvent
            )
        }

        composable(route = Screens.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}