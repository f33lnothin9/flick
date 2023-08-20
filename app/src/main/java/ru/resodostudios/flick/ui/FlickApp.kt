package ru.resodostudios.flick.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.FlickNavigationBar
import ru.resodostudios.flick.core.designsystem.component.FlickNavigationBarItem
import ru.resodostudios.flick.core.designsystem.component.FlickTopAppBar
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.navigation.FlickNavHost
import ru.resodostudios.flick.navigation.TopLevelDestination

@Composable
fun FlickApp(
    appState: FlickAppState = rememberFlickAppState()
) {
    var showSettingsDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            FlickBottomBar(
                destinations = appState.topLevelDestinations,
                onNavigateToDestination = appState::navigateToTopLevelDestination,
                currentDestination = appState.currentDestination
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val destination = appState.currentTopLevelDestination
            if (destination != null) {
                FlickTopAppBar(
                    titleRes = destination.titleTextId,
                    actionIcon = FlickIcons.Settings,
                    actionIconContentDescription = stringResource(
                        id = R.string.top_app_bar_action_icon_description,
                    ),
                    onActionClick = { showSettingsDialog = true }
                )
            }

            FlickNavHost(appState = appState)
        }
    }
}

@Composable
private fun FlickBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    FlickNavigationBar {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            FlickNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false