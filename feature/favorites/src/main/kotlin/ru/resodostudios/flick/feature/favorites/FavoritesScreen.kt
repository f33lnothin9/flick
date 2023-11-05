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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.FlickAsyncImage
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.FavoritePerson
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.feature.movie.MovieViewModel
import ru.resodostudios.flick.feature.person.PersonViewModel

@Composable
internal fun FavoritesRoute(
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    favoriteViewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesState by favoriteViewModel.favoritesUiState.collectAsStateWithLifecycle(
        initialValue = FavoritesUiState.Loading
    )
    FavoritesScreen(
        favoritesState = favoritesState,
        onMovieDelete = favoriteViewModel::favoriteMovieToggle,
        onMovieClick = onMovieClick,
        onPersonClick = onPersonClick,
        onPersonDelete = personViewModel::onEvent
    )
}

@Composable
internal fun FavoritesScreen(
    onMovieClick: (Int) -> Unit,
    favoritesState: FavoritesUiState,
    onMovieDelete: (Movie, Boolean) -> Unit,
    onPersonClick: (Int) -> Unit,
    onPersonDelete: (FavoriteEvent) -> Unit
) {

    when (favoritesState) {
        FavoritesUiState.Loading -> ru.resodostudios.flick.core.ui.LoadingState()
        is FavoritesUiState.Success -> if (favoritesState.data.movies.isNotEmpty() || favoritesState.data.people.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(300.dp)
            ) {
                favoritesMovies(
                    movies = favoritesState.data.movies,
                    onMovieClick = onMovieClick,
                    onMovieDelete = onMovieDelete
                )
                favoritesPeople(
                    people = favoritesState.data.people,
                    onPersonClick = onPersonClick,
                    onPersonDelete = onPersonDelete
                )
            }
        } else {
            ru.resodostudios.flick.core.ui.EmptyState(
                message = stringResource(R.string.favorites_empty),
                animationId = R.raw.anim_empty
            )
        }
    }
}

private fun LazyGridScope.favoritesMovies(
    movies: List<FavoriteMovie>,
    onMovieClick: (Int) -> Unit,
    onMovieDelete: (Movie, Boolean) -> Unit
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
                IconButton(onClick = { onMovieDelete(FavoriteEvent.DeleteMovie(movie)) }) {
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

private fun LazyGridScope.favoritesPeople(
    people: List<FavoritePerson>,
    onPersonClick: (Int) -> Unit,
    onPersonDelete: (FavoriteEvent) -> Unit
) {
    items(people) { person ->
        ListItem(
            headlineContent = { Text(text = person.name) },
            leadingContent = {
                FlickAsyncImage(
                    url = person.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .size(56.dp)
                )
            },
            trailingContent = {
                IconButton(onClick = { onPersonDelete(FavoriteEvent.DeletePerson(person)) }) {
                    Icon(
                        FlickIcons.Delete,
                        contentDescription = "Remove from Favorites"
                    )
                }
            },
            modifier = Modifier.clickable { onPersonClick(person.id) }
        )
    }
}