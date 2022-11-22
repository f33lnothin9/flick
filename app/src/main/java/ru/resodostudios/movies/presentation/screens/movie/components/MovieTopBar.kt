package ru.resodostudios.movies.presentation.screens.movie.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@ExperimentalMaterial3Api
@Composable
fun MovieTopBar(title: String, scrollBehavior: TopAppBarScrollBehavior, onNavClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavClick,
                content = {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            )
        },
        scrollBehavior = scrollBehavior
    )
}