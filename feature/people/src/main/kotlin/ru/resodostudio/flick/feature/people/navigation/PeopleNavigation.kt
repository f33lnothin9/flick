package ru.resodostudio.flick.feature.people.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.resodostudio.flick.feature.people.PeopleRoute

const val PEOPLE_GRAPH_ROUTE_PATTERN = "people_graph"
const val peopleRoute = "people_route"

fun NavController.navigateToPeople(navOptions: NavOptions? = null) {
    this.navigate(peopleRoute, navOptions)
}

fun NavGraphBuilder.peopleGraph(
    onPersonClick: (Int) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = PEOPLE_GRAPH_ROUTE_PATTERN,
        startDestination = peopleRoute,
    ) {
        composable(route = peopleRoute) {
            PeopleRoute(onPersonClick = onPersonClick)
        }
        nestedGraphs()
    }
}