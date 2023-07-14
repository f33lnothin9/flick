package ru.resodostudios.flick.feature.movie.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun MovieTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onNavIconClick: () -> Unit,
    actions: @Composable (RowScope.() -> Unit)
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = onNavIconClick,
                content = {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            )
        },
        scrollBehavior = scrollBehavior,
        actions = actions
    )
}