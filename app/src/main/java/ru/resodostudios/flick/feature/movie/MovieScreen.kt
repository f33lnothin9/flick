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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import ru.resodostudios.flick.core.designsystem.theme.Typography
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.model.data.MovieExtended
import ru.resodostudios.flick.core.ui.BodySection
import ru.resodostudios.flick.core.ui.EmptyState
import ru.resodostudios.flick.core.ui.LoadingState
import ru.resodostudios.flick.feature.favorites.FavoriteEvent
import ru.resodostudios.flick.feature.movie.components.MovieTopBar
import ru.resodostudios.flick.feature.person.formatDate

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
    onEvent: (FavoriteEvent) -> Unit,
    onBackClick: () -> Unit,
    onPersonClick: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    when (movieState) {
        MovieUiState.Loading -> LoadingState()
        is MovieUiState.Success -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    MovieTopBar(
                        scrollBehavior = scrollBehavior,
                        onNavIconClick = onBackClick,
                        movieExtended = movieState.data,
                        onEvent = onEvent
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
                        }
                    )
                }
            )
        }

        is MovieUiState.Error -> EmptyState(
            message = movieState.errorMessage,
            animationId = R.raw.anim_error_2
        )
    }
}

@Composable
private fun MovieHeader(movie: Movie) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        FlickAsyncImage(
            url = movie.image.medium,
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
                    text = movie.rating.average.toString(),
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

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = movie.genres.take(3).joinToString(", "),
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = formatDate(movie.premiered),
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = "${movie.network.country.name}, ${movie.network.country.code}",
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = movie.status + ", ${movie.averageRuntime} ${stringResource(R.string.minutes)}",
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = movie.language,
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
                style = Typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = HtmlCompat.fromHtml(
                    movieExtended.movie.summary,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                ).toString(),
                style = Typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (movieExtended.cast.isNotEmpty()) {
            BodySection(
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
            BodySection(
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
    }
}