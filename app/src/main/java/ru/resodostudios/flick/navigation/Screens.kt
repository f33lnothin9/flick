package ru.resodostudios.flick.navigation

import ru.resodostudios.flick.core.Constants.FAVORITES_SCREEN
import ru.resodostudios.flick.core.Constants.MOVIES_SCREEN
import ru.resodostudios.flick.core.Constants.MOVIE_SCREEN
import ru.resodostudios.flick.core.Constants.PEOPLE_SCREEN
import ru.resodostudios.flick.core.Constants.SETTINGS_SCREEN

sealed class Screens(val route: String) {

    data object Movies: Screens(MOVIES_SCREEN)

    data object Movie: Screens(MOVIE_SCREEN)

    data object Favorites: Screens(FAVORITES_SCREEN)

    data object Settings: Screens(SETTINGS_SCREEN)

    data object People: Screens(PEOPLE_SCREEN)
}