package ru.resodostudios.flick.feature.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.FlickAsyncImage
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.ui.EmptyState
import ru.resodostudios.flick.core.ui.LoadingState
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.flick.feature.movie.presentation.MovieViewModel

@Composable
internal fun FavoritesRoute(
    onMovieClick: (Int) -> Unit,
    favoriteViewModel: FavoritesViewModel = hiltViewModel(),
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val favoritesState by favoriteViewModel.favoritesUiState.collectAsStateWithLifecycle(
        initialValue = FavoritesUiState.Loading
    )

    FavoritesScreen(
        favoritesState = favoritesState,
        onDeleteClick = movieViewModel::onEvent,
        onMovieClick = onMovieClick
    )
}

@Composable
internal fun FavoritesScreen(
    onMovieClick: (Int) -> Unit,
    favoritesState: FavoritesUiState,
    onDeleteClick: (FavoriteEvent) -> Unit
) {

    when (favoritesState) {
        FavoritesUiState.Loading -> LoadingState()
        is FavoritesUiState.Success -> if (favoritesState.movies.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(300.dp)
            ) {
                favorites(
                    movies = favoritesState.movies,
                    onMovieClick = onMovieClick,
                    onDeleteClick = onDeleteClick
                )
            }
        } else {
            EmptyState(
                message = "Nothing in Favorites",
                animationId = R.raw.anim_empty
            )
        }
    }
}

private fun LazyGridScope.favorites(
    movies: List<FavoriteMovie>,
    onMovieClick: (Int) -> Unit,
    onDeleteClick: (FavoriteEvent) -> Unit
) {
    items(movies) { movie ->
        ListItem(
            headlineContent = { Text(text = movie.name) },
            leadingContent = {
                FlickAsyncImage(
                    url = movie.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .size(56.dp)
                )
            },
            trailingContent = {
                IconButton(onClick = { onDeleteClick(FavoriteEvent.DeleteMovie(movie)) }) {
                    Icon(
                        FlickIcons.Delete,
                        contentDescription = "Remove from Favorites"
                    )
                }
            },
            supportingContent = {
                Text(
                    text = "${movie.rating} • ${
                        movie.genres?.take(2)?.joinToString(", ")
                    }"
                )
            },
            modifier = Modifier.clickable { onMovieClick(movie.id) }
        )
    }
}
