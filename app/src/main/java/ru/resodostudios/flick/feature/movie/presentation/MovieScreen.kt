package ru.resodostudios.flick.feature.movie.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.FlickAsyncImage
import ru.resodostudios.flick.core.designsystem.component.RetrySection
import ru.resodostudios.flick.core.designsystem.theme.Typography
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.network.model.Movie
import ru.resodostudios.flick.core.ui.LoadingState
import ru.resodostudios.flick.feature.favorites.FavoritesUiState
import ru.resodostudios.flick.feature.favorites.FavoritesViewModel
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.flick.feature.movie.presentation.components.MovieTopBar

@Composable
internal fun MovieRoute(
    movieViewModel: MovieViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    movieId: Int
) {

    val movieState by movieViewModel.state.collectAsStateWithLifecycle()
    val favoritesState by favoritesViewModel.favoritesUiState.collectAsStateWithLifecycle(
        initialValue = FavoritesUiState.Loading
    )

    movieViewModel.getMovie(movieId)
    movieViewModel.getCast(movieId)
    movieViewModel.getCrew(movieId)

    MovieScreen(
        movieState = movieState,
        favoritesState = favoritesState,
        onEvent = movieViewModel::onEvent,
        onRetry = {
            movieViewModel.getMovie(movieId)
            movieViewModel.getCast(movieId)
            movieViewModel.getCrew(movieId)
        },
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieScreen(
    movieState: MovieUiState,
    favoritesState: FavoritesUiState,
    onEvent: (FavoriteEvent) -> Unit,
    onRetry: () -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var isFavorite by rememberSaveable { mutableStateOf(false) }

    when (favoritesState) {
        FavoritesUiState.Loading -> LoadingState()
        is FavoritesUiState.Success -> {
            for (favoriteMovie in favoritesState.movies) {
                isFavorite = favoriteMovie.id == movieState.movie.id
            }
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    MovieTopBar(
                        scrollBehavior = scrollBehavior,
                        onNavIconClick = onBackClick,
                        actions = {
                            if (isFavorite) {
                                IconButton(
                                    onClick = {
                                        onEvent(
                                            FavoriteEvent.DeleteMovie(
                                                FavoriteMovie(
                                                    id = movieState.movie.id,
                                                    name = movieState.movie.name,
                                                    genres = movieState.movie.genres,
                                                    rating = movieState.movie.rating?.average,
                                                    image = movieState.movie.image?.medium.toString()
                                                )
                                            )
                                        )
                                        isFavorite = false
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Favorite,
                                        contentDescription = "Favorite"
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        onEvent(FavoriteEvent.AddMovie(movieState.movie))
                                        isFavorite = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite"
                                    )
                                }
                            }
                        }
                    )
                },
                contentWindowInsets = WindowInsets.waterfall,
                content = {
                    AnimatedVisibility(visible = !movieState.isLoading, enter = fadeIn()) {
                        LazyColumn(
                            contentPadding = it,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            content = {
                                item {
                                    Header(movie = movieState.movie)
                                }
                                item {
                                    Body(
                                        state = movieState
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    }

    if (movieState.isLoading) LoadingState()
    if (movieState.isError) RetrySection(onClick = onRetry)
}

@Composable
private fun Header(movie: Movie) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        FlickAsyncImage(
            url = movie.image?.medium.toString(),
            contentDescription = null,
            modifier = Modifier
                .size(width = 125.dp, height = 176.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.FillWidth
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = movie.name,
                style = Typography.headlineSmall,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Surface(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = movie.rating?.average.toString(),
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 2.dp,
                            end = 8.dp,
                            bottom = 2.dp
                        ),
                    style = Typography.labelLarge,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {
                Text(
                    text = movie.premiered?.take(4) + " • " + movie.genres?.take(2)
                        ?.joinToString(", "),
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = (movie.network?.country?.name
                        ?: "Unknown") + ", ${movie.averageRuntime} minutes",
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = movie.language ?: "Unknown",
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    HorizontalDivider()
}

@Composable
private fun Body(state: MovieUiState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.summary),
                style = Typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = HtmlCompat.fromHtml(
                    state.movie.summary.toString(),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                ).toString(),
                style = Typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        var castRows by remember { mutableIntStateOf(4) }

        when (state.cast.size) {
            3 -> castRows = 3
            2 -> castRows = 2
            1 -> castRows = 1
        }

        if (state.cast.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Cast",
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 16.dp)
                )

                LazyHorizontalGrid(
                    rows = GridCells.Fixed(castRows),
                    modifier = Modifier
                        .then(
                            when (state.cast.size) {
                                3 -> Modifier.height(180.dp)
                                2 -> Modifier.height(120.dp)
                                1 -> Modifier.height(60.dp)
                                else -> Modifier.height(240.dp)
                            }
                        )
                ) {
                    items(state.cast) { cast ->
                        ListItem(
                            headlineContent = { Text(text = cast.person?.name.toString()) },
                            leadingContent = {
                                Box {
                                    FlickAsyncImage(
                                        url = cast.person?.image?.medium.toString(),
                                        contentDescription = "Person image",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(100))
                                            .size(40.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            },
                            supportingContent = { Text(text = cast.character?.name.toString()) }
                        )
                    }
                }
            }
        }

        if (state.crew.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Crew",
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 16.dp)
                )

                LazyRow {
                    items(state.crew) { crew ->
                        ListItem(
                            headlineContent = { Text(text = crew.person?.name.toString()) },
                            leadingContent = {
                                Box {
                                    FlickAsyncImage(
                                        url = crew.person?.image?.medium.toString(),
                                        contentDescription = "Person image",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(100))
                                            .size(40.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            },
                            supportingContent = { Text(text = crew.type.toString()) }
                        )
                    }
                }
            }
        }
    }
}