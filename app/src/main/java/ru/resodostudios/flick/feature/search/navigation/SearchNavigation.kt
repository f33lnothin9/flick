package ru.resodostudios.flick.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val searchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
) {
    composable(route = searchRoute) {

    }
}
