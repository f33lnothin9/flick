package ru.resodostudio.flick.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.resodostudio.flick.feature.settings.SettingsRoute

const val settingsRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen(
    onBackClick: () -> Unit
) {
    composable(route = settingsRoute) {
        SettingsRoute(
            onBackClick = onBackClick
        )
    }
}