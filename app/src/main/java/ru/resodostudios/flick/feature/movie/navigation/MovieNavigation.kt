package ru.resodostudios.flick.feature.movie.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.resodostudios.flick.feature.movie.presentation.MovieRoute

internal const val movieIdArg = "movieId"

fun NavController.navigateToMovie(movieId: Int) {
    this.navigate("movie_route/$movieId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.movieScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "movie_route/{$movieIdArg}",
        arguments = listOf(
            navArgument(movieIdArg) { type = NavType.IntType },
        )
    ) {
        val movieId = remember { it.arguments?.getInt(movieIdArg) }

        if (movieId != null) {
            MovieRoute(
                onBackClick = onBackClick,
                movieId = movieId
            )
        }
    }
}