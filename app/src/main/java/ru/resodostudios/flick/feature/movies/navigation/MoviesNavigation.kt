package ru.resodostudios.flick.feature.movies.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.resodostudios.flick.feature.movies.MoviesRoute

const val moviesNavigationRoute = "movies_route"

fun NavController.navigateToMovies(navOptions: NavOptions? = null) {
    this.navigate(moviesNavigationRoute, navOptions)
}

fun NavGraphBuilder.moviesScreen() {
    composable(
        route = moviesNavigationRoute
    ) {
        MoviesRoute()
    }
}