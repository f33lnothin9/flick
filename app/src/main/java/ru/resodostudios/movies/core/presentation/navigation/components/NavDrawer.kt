package ru.resodostudios.movies.core.presentation.navigation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.resodostudios.movies.core.presentation.navigation.NavDrawerItem
import ru.resodostudios.movies.core.presentation.theme.Typography

@Composable
fun NavDrawer(navController: NavHostController, drawerState: DrawerState, content: @Composable () -> Unit) {

    val screens = listOf(
        NavDrawerItem.Movies,
        NavDrawerItem.People,
        NavDrawerItem.Favorites,
        NavDrawerItem.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val gesturesEnabled = screens.any { it.route == currentDestination?.route }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Flick",
                    modifier = Modifier.padding(start = 32.dp, top = 8.dp, bottom = 24.dp),
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                screens.forEach { screen ->
                    DrawerItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController,
                        drawerState = drawerState,
                        scope = scope
                    )
                }
            }
        },
        gesturesEnabled = gesturesEnabled,
        content = content
    )
}

@Composable
fun DrawerItem(
    screen: NavDrawerItem,
    currentDestination: NavDestination?,
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    NavigationDrawerItem(
        label = {
            Text(
                text = screen.title,
                maxLines = 1
            )
        },
        icon = { Icon(screen.icon, contentDescription = null) },
        selected = selected,
        onClick = {
            if (currentDestination?.route != screen.route) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
            scope.launch { drawerState.close() }
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}