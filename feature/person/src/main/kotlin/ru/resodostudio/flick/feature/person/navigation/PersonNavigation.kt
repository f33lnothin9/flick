package ru.resodostudio.flick.feature.person.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.resodostudio.flick.feature.person.PersonRoute

internal const val personIdArg = "personId"

internal class PersonArgs(val personId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle.get<Int>(personIdArg) ?: 0)
}

fun NavController.navigateToPerson(personId: Int) {
    this.navigate("person_route/$personId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.personScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    composable(
        route = "person_route/{$personIdArg}",
        arguments = listOf(
            navArgument(personIdArg) { type = NavType.IntType },
        )
    ) {
        PersonRoute(
            onBackClick = onBackClick,
            onMovieClick = onMovieClick
        )
    }
}