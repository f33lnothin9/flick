package ru.resodostudios.movies.core.presentation.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.CoroutineScope
import ru.resodostudios.movies.feature.movie.presentation.MovieScreen
import ru.resodostudios.movies.feature.movies.presentation.MoviesScreen

@ExperimentalMaterial3Api
@Composable
fun NavHost(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    ) {
        composable(route = Screens.Main.route) {
            MoviesScreen(navController = navController, drawerState = drawerState, scope = scope)
        }

        composable(route = Screens.Movie.route + "/{id}") { backStackEntry ->
            MovieScreen(navController = navController, movieId = backStackEntry.arguments?.getString("id")  ?: "1")
        }

        composable(route = Screens.Favorites.route) {

        }

        composable(route = Screens.Settings.route) {

        }
    }
}