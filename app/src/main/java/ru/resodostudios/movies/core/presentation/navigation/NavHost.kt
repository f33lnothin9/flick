package ru.resodostudios.movies.core.presentation.navigation

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
import ru.resodostudios.movies.feature.favorites.presentation.FavoritesScreen
import ru.resodostudios.movies.feature.favorites.presentation.FavoritesViewModel
import ru.resodostudios.movies.feature.movie.presentation.MovieScreen
import ru.resodostudios.movies.feature.movie.presentation.MovieViewModel
import ru.resodostudios.movies.feature.movies.presentation.MoviesScreen

@ExperimentalMaterial3Api
@Composable
fun NavHost(
    navController: NavHostController,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    ) {
        composable(route = Screens.Main.route) {
            MoviesScreen(navController = navController, drawerState = drawerState)
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

            id?.let {
                MovieScreen(
                    navController = navController,
                    movieId = it
                )
            }
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

        }
    }
}