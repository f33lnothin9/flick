package ru.resodostudios.movies.presentation.navigation

import ru.resodostudios.movies.util.Constants

sealed class Screens(val route: String) {
    object Main: Screens(route = Constants.MAIN_SCREEN)
    object Movie: Screens(route = Constants.MOVIE_SCREEN)
}
