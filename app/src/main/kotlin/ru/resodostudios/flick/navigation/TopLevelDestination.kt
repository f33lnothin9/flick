package ru.resodostudios.flick.navigation

import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: Int,
    val titleTextId: Int
) {
    HOME(
        selectedIcon = FlickIcons.HomeFilled,
        unselectedIcon = FlickIcons.Home,
        iconTextId = R.string.home,
        titleTextId = R.string.app_name
    ),
    MOVIES(
        selectedIcon = FlickIcons.MoviesFilled,
        unselectedIcon = FlickIcons.Movies,
        iconTextId = R.string.movies,
        titleTextId = R.string.movies
    ),
    TV_SHOWS(
        selectedIcon = FlickIcons.TvShowFilled,
        unselectedIcon = FlickIcons.TvShow,
        iconTextId = R.string.tv_shows,
        titleTextId = R.string.tv_shows
    ),
    PEOPLE(
        selectedIcon = FlickIcons.PeopleFilled,
        unselectedIcon = FlickIcons.People,
        iconTextId = R.string.people,
        titleTextId = R.string.people
    )
}