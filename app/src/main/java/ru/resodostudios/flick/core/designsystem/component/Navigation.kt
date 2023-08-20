package ru.resodostudios.flick.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable

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