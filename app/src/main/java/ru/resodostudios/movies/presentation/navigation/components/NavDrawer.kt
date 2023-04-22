package ru.resodostudios.movies.presentation.navigation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.resodostudios.movies.presentation.navigation.NavItem

@Composable
fun NavDrawer(navController: NavHostController, drawerState: DrawerState, scope: CoroutineScope, content: @Composable () -> Unit) {

    val screens = listOf(
        NavItem.Movies,
        NavItem.Favorites,
        NavItem.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))

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
        content = content
    )
}

@Composable
fun DrawerItem(
    screen: NavItem,
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