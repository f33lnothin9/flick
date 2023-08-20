package ru.resodostudios.flick.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    MOVIES(
        selectedIcon = FlickIcons.MoviesFilled,
        unselectedIcon = FlickIcons.Movies,
        iconTextId = R.string.movies,
        titleTextId = R.string.app_name
    ),
    PEOPLE(
        selectedIcon = FlickIcons.PeopleFilled,
        unselectedIcon = FlickIcons.People,
        iconTextId = R.string.people,
        titleTextId = R.string.people
    ),
    FAVORITES(
        selectedIcon = FlickIcons.FavoritesFilled,
        unselectedIcon = FlickIcons.Favorites,
        iconTextId = R.string.favorites,
        titleTextId = R.string.favorites
    )
}