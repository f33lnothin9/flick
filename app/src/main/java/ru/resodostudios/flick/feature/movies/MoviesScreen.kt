package ru.resodostudios.flick.feature.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.ui.EmptyState
import ru.resodostudios.flick.core.ui.LoadingState
import ru.resodostudios.flick.feature.movies.component.MovieCard

@Composable
internal fun MoviesRoute(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val moviesState by viewModel.moviesUiState.collectAsStateWithLifecycle()

    MoviesScreen(
        moviesState = moviesState,
        onMovieClick = onMovieClick
    )
}

@Composable
internal fun MoviesScreen(
    moviesState: MoviesUiState,
    onMovieClick: (Int) -> Unit
) {

    when (moviesState) {
        MoviesUiState.Loading -> LoadingState()
        is MoviesUiState.Success -> LazyVerticalStaggeredGrid(
            contentPadding = PaddingValues(16.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            columns = StaggeredGridCells.Adaptive(150.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            movies(
                movies = moviesState.movies,
                onMovieClick = onMovieClick
            )
        }

        is MoviesUiState.Error -> EmptyState(
            message = moviesState.errorMessage,
            animationId = R.raw.anim_error_2
        )
    }
}

private fun LazyStaggeredGridScope.movies(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    items(movies) { movie ->
        MovieCard(
            movie = movie,
            onMovieClick = onMovieClick
        )
    }
}