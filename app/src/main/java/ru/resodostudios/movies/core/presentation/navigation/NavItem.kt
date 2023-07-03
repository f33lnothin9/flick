package ru.resodostudios.movies.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Movies : NavItem(
        route = Screens.Main.route,
        title = "Movies",
        icon = Icons.Outlined.Movie
    )

    object Favorites : NavItem(
        route = Screens.Favorites.route,
        title = "Favorites",
        icon = Icons.Outlined.FavoriteBorder
    )

    object Settings : NavItem(
        route = Screens.Settings.route,
        title = "Settings",
        icon = Icons.Outlined.Settings
    )
}