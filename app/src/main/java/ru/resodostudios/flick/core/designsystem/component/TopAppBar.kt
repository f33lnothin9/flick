package ru.resodostudios.flick.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    actionIcon: ImageVector,
    actionIconContentDescription: String?,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoTitleTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onNavIconClick: () -> Unit,
    actions: @Composable (RowScope.() -> Unit),
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = onNavIconClick,
                content = {
                    Icon(imageVector = FlickIcons.ArrowBack, contentDescription = "Back")
                }
            )
        },
        scrollBehavior = scrollBehavior,
        actions = actions
    )
}