package ru.resodostudios.flick.feature.movie

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.common.convertPixelsToDp
import ru.resodostudios.flick.core.common.formatDate
import ru.resodostudios.flick.core.designsystem.component.FlickAsyncImage
import ru.resodostudios.flick.core.designsystem.component.NoTitleTopAppBar
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.model.data.MovieExtended
import ru.resodostudios.flick.feature.favorites.FavoriteEvent

@Composable
internal fun MovieRoute(
    movieViewModel: MovieViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onPersonClick: (Int) -> Unit
) {
    val movieState by movieViewModel.movieUiState.collectAsStateWithLifecycle()

    MovieScreen(
        movieState = movieState,
        onEvent = movieViewModel::onEvent,
        onBackClick = onBackClick,
        onPersonClick = onPersonClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieScreen(
    movieState: MovieUiState,
    onEvent: (ru.resodostudios.flick.feature.favorites.FavoriteEvent) -> Unit,
    onBackClick: () -> Unit,
    onPersonClick: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    when (movieState) {
        MovieUiState.Loading -> ru.resodostudios.flick.core.ui.LoadingState()
        is MovieUiState.Success -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    NoTitleTopAppBar(
                        scrollBehavior = scrollBehavior,
                        onNavIconClick = onBackClick,
                        actions = {
                            if (movieState.data.isFavorite) {
                                IconButton(
                                    onClick = {
                                        onEvent(
                                            ru.resodostudios.flick.feature.favorites.FavoriteEvent.DeleteMovie(
                                                FavoriteMovie(
                                                    id = movieState.data.movie.id,
                                                    name = movieState.data.movie.name,
                                                    genres = movieState.data.movie.genres,
                                                    rating = movieState.data.movie.rating.average,
                                                    image = movieState.data.movie.image.medium
                                                )
                                            )
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = FlickIcons.FavoritesFilled,
                                        contentDescription = "Remove from Favorites"
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = { onEvent(ru.resodostudios.flick.feature.favorites.FavoriteEvent.AddMovie(movieState.data.movie)) }
                                ) {
                                    Icon(
                                        imageVector = FlickIcons.Favorites,
                                        contentDescription = "Add to Favorites"
                                    )
                                }
                            }
                        }
                    )
                },
                contentWindowInsets = WindowInsets.waterfall,
                content = {
                    LazyColumn(
                        contentPadding = it,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            item {
                                MovieHeader(movie = movieState.data.movie)
                            }
                            item {
                                MovieBody(
                                    movieExtended = movieState.data,
                                    onPersonClick = onPersonClick
                                )
                            }
                            item { Spacer(modifier = Modifier.height(50.dp)) }
                        }
                    )
                }
            )
        }

        is MovieUiState.Error -> ru.resodostudios.flick.core.ui.EmptyState(
            message = movieState.errorMessage,
            animationId = R.raw.anim_error_2
        )
    }
}

@Composable
private fun MovieHeader(movie: Movie) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        FlickAsyncImage(
            url = movie.image.medium,
            contentDescription = null,
            modifier = Modifier
                .size(width = 130.dp, height = 181.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = movie.name,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Surface(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = movie.rating.average.toString(),
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 2.dp,
                            end = 8.dp,
                            bottom = 2.dp
                        ),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                if (movie.genres.isNotEmpty()) {
                    Text(
                        text = movie.genres.take(3).joinToString(", "),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (movie.premiered.isNotBlank()) {
                    Text(
                        text = formatDate(movie.premiered),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Start
                    )
                }

                val countryInfo = listOf(movie.network.country.name, movie.network.country.code)
                if (movie.network.country.name.isNotBlank()) {
                    Text(
                        text = countryInfo.joinToString(", "),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Start
                    )
                }

                val statusInfo = listOf(
                    movie.status,
                    "${movie.averageRuntime} ${stringResource(R.string.minutes)}"
                )
                Text(
                    text = statusInfo.joinToString(", "),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                if (movie.language.isNotBlank()) {
                    Text(
                        text = movie.language,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    HorizontalDivider()
}

@Composable
private fun MovieBody(
    movieExtended: MovieExtended,
    onPersonClick: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.summary),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = HtmlCompat.fromHtml(
                    movieExtended.movie.summary,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                ).toString(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        ru.resodostudios.flick.core.ui.AdBanner(id = R.string.banner_id)

        if (movieExtended.cast.isNotEmpty()) {
            ru.resodostudios.flick.core.ui.BodySection(
                title = R.string.cast,
                itemsSize = movieExtended.cast.size,
                content = {
                    items(movieExtended.cast) { cast ->
                        ListItem(
                            headlineContent = { Text(text = cast.person.name) },
                            leadingContent = {
                                Box {
                                    FlickAsyncImage(
                                        url = cast.person.image.medium,
                                        contentDescription = "Person image",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .size(56.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            },
                            supportingContent = { Text(text = cast.character.name) },
                            modifier = Modifier.clickable { onPersonClick(cast.person.id) }
                        )
                    }
                }
            )
        }

        if (movieExtended.crew.isNotEmpty()) {
            ru.resodostudios.flick.core.ui.BodySection(
                title = R.string.crew,
                itemsSize = movieExtended.crew.size,
                content = {
                    items(movieExtended.crew) { crew ->
                        ListItem(
                            headlineContent = { Text(text = crew.person.name) },
                            leadingContent = {
                                Box {
                                    FlickAsyncImage(
                                        url = crew.person.image.medium,
                                        contentDescription = "Person image",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .size(56.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            },
                            supportingContent = { Text(text = crew.type) },
                            modifier = Modifier.clickable { onPersonClick(crew.person.id) }
                        )
                    }
                }
            )
        }

        if (movieExtended.images.isNotEmpty()) {
            val context = LocalContext.current

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.images),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 16.dp)
                )

                LazyHorizontalStaggeredGrid(
                    rows = StaggeredGridCells.Adaptive(150.dp),
                    modifier = Modifier
                        .height(400.dp)
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        items(movieExtended.images) { imageExtended ->
                            FlickAsyncImage(
                                url = imageExtended.resolutions.original.url,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .size(
                                        height = convertPixelsToDp(
                                            context = context,
                                            pixels = imageExtended.resolutions.original.height.div(
                                                2f
                                            )
                                        ),
                                        width = convertPixelsToDp(
                                            context = context,
                                            pixels = imageExtended.resolutions.original.width.div(2f)
                                        )
                                    ),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                )
            }
        }
    }
}