package ru.resodostudios.flick.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.data.util.NetworkMonitor
import ru.resodostudios.flick.core.designsystem.component.FlickNavigationBar
import ru.resodostudios.flick.core.designsystem.component.FlickNavigationBarItem
import ru.resodostudios.flick.core.designsystem.component.FlickNavigationRail
import ru.resodostudios.flick.core.designsystem.component.FlickNavigationRailItem
import ru.resodostudios.flick.core.designsystem.component.FlickTopAppBar
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.navigation.FlickNavHost
import ru.resodostudios.flick.navigation.TopLevelDestination

@Composable
fun FlickApp(
    networkMonitor: NetworkMonitor,
    windowSizeClass: WindowSizeClass,
    appState: FlickAppState = rememberFlickAppState(
        networkMonitor = networkMonitor,
        windowSizeClass = windowSizeClass
    )
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val notConnectedMessage = stringResource(R.string.not_connected)
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    var showSettingsDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                FlickBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination
                )
            }
        }
    ) { innerPadding ->

        Row(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            if (appState.shouldShowNavRail) {
                FlickNavRail(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.safeDrawingPadding()
                )
            }

            Column(Modifier.fillMaxSize()) {
                val destination = appState.currentTopLevelDestination
                if (destination != null) {
                    FlickTopAppBar(
                        titleRes = destination.titleTextId,
                        navigationIcon = FlickIcons.Search,
                        navigationIconContentDescription = stringResource(
                            id = R.string.top_app_bar_navigation_icon_description
                        ),
                        actionIcon = FlickIcons.Settings,
                        actionIconContentDescription = stringResource(
                            id = R.string.top_app_bar_action_icon_description,
                        ),
                        onNavigationClick = { appState.navigateToSearch() },
                        onActionClick = { showSettingsDialog = true }
                    )
                }

                FlickNavHost(appState = appState)
            }
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

@Composable
private fun FlickNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    FlickNavigationRail(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column {
                destinations.forEach { destination ->
                    val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                    FlickNavigationRailItem(
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
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false