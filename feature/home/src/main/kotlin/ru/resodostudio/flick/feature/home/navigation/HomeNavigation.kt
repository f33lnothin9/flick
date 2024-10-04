package ru.resodostudio.flick.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val HOME_GRAPH_ROUTE_PATTERN = "home_graph"
const val homeRoute = "home_route"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.homeGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = HOME_GRAPH_ROUTE_PATTERN,
        startDestination = homeRoute,
    ) {
        composable(route = homeRoute) {

        }
        nestedGraphs()
    }
}