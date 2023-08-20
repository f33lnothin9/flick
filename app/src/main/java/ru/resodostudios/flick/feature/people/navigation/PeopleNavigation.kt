package ru.resodostudios.flick.feature.people.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.resodostudios.flick.feature.people.PeopleRoute

const val peopleNavigationRoute = "people_route"

fun NavController.navigateToPeople(navOptions: NavOptions? = null) {
    this.navigate(peopleNavigationRoute, navOptions)
}

fun NavGraphBuilder.peopleScreen() {
    composable(
        route = peopleNavigationRoute
    ) {
        PeopleRoute()
    }
}