package ru.resodostudios.movies.core.presentation.navigation

import ru.resodostudios.movies.core.Constants

sealed class Screens(val route: String) {
    object Main: Screens(Constants.MAIN_SCREEN)
    object Movie: Screens(Constants.MOVIE_SCREEN)
    object Favorites: Screens(Constants.FAVORITES_SCREEN)
    object Settings: Screens(Constants.SETTINGS_SCREEN)
}