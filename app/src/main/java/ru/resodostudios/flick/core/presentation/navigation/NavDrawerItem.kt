package ru.resodostudios.flick.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraRoll
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDrawerItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Movies : NavDrawerItem(
        route = Screens.Main.route,
        title = "Movies",
        icon = Icons.Outlined.CameraRoll
    )

    object People : NavDrawerItem(
        route = Screens.People.route,
        title = "People",
        icon = Icons.Outlined.People
    )

    object Favorites : NavDrawerItem(
        route = Screens.Favorites.route,
        title = "Favorites",
        icon = Icons.Outlined.FavoriteBorder
    )

    object Settings : NavDrawerItem(
        route = Screens.Settings.route,
        title = "Settings",
        icon = Icons.Outlined.Settings
    )
}