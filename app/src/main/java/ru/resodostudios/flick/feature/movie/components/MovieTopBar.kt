package ru.resodostudios.flick.feature.movie.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.MovieExtended
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onNavIconClick: () -> Unit,
    movieExtended: MovieExtended,
    onEvent: (FavoriteEvent) -> Unit
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
        actions = {
            if (movieExtended.isFavorite) {
                IconButton(
                    onClick = {
                        onEvent(
                            FavoriteEvent.DeleteMovie(
                                FavoriteMovie(
                                    id = movieExtended.movie.id,
                                    name = movieExtended.movie.name,
                                    genres = movieExtended.movie.genres,
                                    rating = movieExtended.movie.rating.average,
                                    image = movieExtended.movie.image.medium
                                )
                            )
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Favorite"
                    )
                }
            } else {
                IconButton(
                    onClick = { onEvent(FavoriteEvent.AddMovie(movieExtended.movie)) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
        }
    )
}