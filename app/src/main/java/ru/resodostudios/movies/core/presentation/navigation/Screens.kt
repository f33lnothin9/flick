package ru.resodostudios.movies.core.presentation.navigation

import ru.resodostudios.movies.core.Constants.FAVORITES_SCREEN
import ru.resodostudios.movies.core.Constants.MAIN_SCREEN
import ru.resodostudios.movies.core.Constants.MOVIE_SCREEN
import ru.resodostudios.movies.core.Constants.PEOPLE_SCREEN
import ru.resodostudios.movies.core.Constants.SETTINGS_SCREEN

sealed class Screens(val route: String) {

    object Main: Screens(MAIN_SCREEN)

    object Movie: Screens(MOVIE_SCREEN)

    object Favorites: Screens(FAVORITES_SCREEN)

    object Settings: Screens(SETTINGS_SCREEN)

    object People: Screens(PEOPLE_SCREEN)
}