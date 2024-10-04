package ru.resodostudio.flick.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RowScope.FlickNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel
    )
}

@Composable
fun FlickNavigationBar(
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(content = content)
}

@Composable
fun FlickNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel
    )
}

@Composable
fun FlickNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        header = header,
        content = content
    )
}