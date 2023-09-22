package ru.resodostudios.flick.feature.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.resodostudios.flick.feature.favorites.FavoritesRoute

const val favoritesNavigationRoute = "favorites_route"

fun NavController.navigateToFavorites(navOptions: NavOptions? = null) {
    this.navigate(favoritesNavigationRoute, navOptions)
}

fun NavGraphBuilder.favoritesScreen(
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit
) {
    composable(
        route = favoritesNavigationRoute
    ) {
        FavoritesRoute(
            onMovieClick = onMovieClick,
            onPersonClick = onPersonClick
        )
    }
}