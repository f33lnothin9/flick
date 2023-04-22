package ru.resodostudios.movies.presentation.navigation

import ru.resodostudios.movies.util.Constants

sealed class Screens(val route: String) {
    object Main: Screens(Constants.MAIN_SCREEN)
    object Movie: Screens(Constants.MOVIE_SCREEN)
    object Favorites: Screens(Constants.FAVORITES_SCREEN)
    object Settings: Screens(Constants.SETTINGS_SCREEN)
}