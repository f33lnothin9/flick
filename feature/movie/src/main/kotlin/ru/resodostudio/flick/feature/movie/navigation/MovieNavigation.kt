package ru.resodostudio.flick.feature.movie.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.resodostudio.flick.feature.movie.MovieRoute

internal const val movieIdArg = "movieId"

internal class MovieArgs(val movieId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle.get<Int>(movieIdArg) ?: 0)
}

fun NavController.navigateToMovie(movieId: Int) {
    this.navigate("movie_route/$movieId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.movieScreen(
    onBackClick: () -> Unit,
    onPersonClick: (Int) -> Unit
) {
    composable(
        route = "movie_route/{$movieIdArg}",
        arguments = listOf(
            navArgument(movieIdArg) { type = NavType.IntType },
        )
    ) {
        MovieRoute(
            onBackClick = onBackClick,
            onPersonClick = onPersonClick
        )
    }
}