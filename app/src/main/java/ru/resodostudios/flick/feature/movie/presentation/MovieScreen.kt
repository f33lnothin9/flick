package ru.resodostudios.flick.feature.movie.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.presentation.components.CoilImage
import ru.resodostudios.flick.core.presentation.components.RetrySection
import ru.resodostudios.flick.core.presentation.theme.Typography
import ru.resodostudios.flick.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.flick.feature.movie.data.model.Movie
import ru.resodostudios.flick.feature.movie.presentation.components.MovieTopBar

@ExperimentalMaterial3Api
@Composable
fun MovieScreen(
    navController: NavController,
    state: MovieUiState,
    onEvent: (FavoriteEvent) -> Unit,
    onRetry: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current

    var maxLines by remember { mutableIntStateOf(3) }

    Box {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MovieTopBar(
                    scrollBehavior = scrollBehavior,
                    onNavIconClick = { navController.navigateUp() },
                    actions = {
                        IconButton(
                            onClick = {
                                onEvent(FavoriteEvent.AddMovie(state.movie))
                                Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite"
                            )
                        }
                    }
                )
            },
            contentWindowInsets = WindowInsets.waterfall,
            content = {
                AnimatedVisibility(visible = !state.isLoading, enter = fadeIn()) {
                    LazyColumn(
                        contentPadding = it,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            item {
                                Header(movie = state.movie)
                            }
                            item {
                                Body(
                                    state = state,
                                    onSummaryClick = {
                                        maxLines = if (maxLines == 3) Int.MAX_VALUE else 3
                                    },
                                    maxLines = maxLines
                                )
                            }
                        }
                    )
                }
            }
        )

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (state.isError) {
            RetrySection(onClick = onRetry)
        }
    }
}

@Composable
private fun Header(movie: Movie) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        CoilImage(
            url = movie.image?.medium.toString(),
            width = 125.dp,
            height = 176.dp
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = movie.name.toString(),
                style = Typography.headlineSmall,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Surface(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = (movie.rating?.average ?: 0.0).toString(),
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

@ExperimentalMaterial3Api
@Composable
private fun Body(state: MovieUiState, onSummaryClick: () -> Unit, maxLines: Int) {

    Spacer(modifier = Modifier.height(8.dp))

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            onClick = onSummaryClick
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Summary",
                    style = Typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = HtmlCompat.fromHtml(
                        state.movie.summary ?: "",
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    ).toString(),
                    style = Typography.bodyLarge,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
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
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Cast",
                    style = Typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 32.dp, top = 16.dp)
                )

                LazyHorizontalGrid(
                    rows = GridCells.Fixed(castRows),
                    modifier = Modifier
                        .then(
                            when (state.cast.size) {
                                3 -> Modifier.height(216.dp)
                                2 -> Modifier.height(144.dp)
                                1 -> Modifier.height(72.dp)
                                else -> Modifier.height(288.dp)
                            }
                        )
                ) {
                    items(state.cast) { cast ->
                        ListItem(
                            modifier = Modifier.padding(start = 16.dp),
                            headlineContent = { Text(text = cast.person?.name.toString()) },
                            leadingContent = {
                                Box {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(cast.person?.image?.medium)
                                            .crossfade(400)
                                            .size(256)
                                            .error(if (isSystemInDarkTheme()) R.drawable.ic_outlined_face_white else R.drawable.ic_outlined_face)
                                            .transformations(CircleCropTransformation())
                                            .build(),
                                        contentDescription = "Image",
                                        modifier = Modifier.size(56.dp),
                                        filterQuality = FilterQuality.Low
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
                    style = Typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 32.dp)
                )

                LazyRow {
                    items(state.crew) { crew ->
                        ListItem(
                            modifier = Modifier.padding(start = 16.dp),
                            headlineContent = { Text(text = crew.person?.name.toString()) },
                            leadingContent = {
                                Box {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(crew.person?.image?.medium)
                                            .crossfade(400)
                                            .size(256)
                                            .error(if (isSystemInDarkTheme()) R.drawable.ic_outlined_face_white else R.drawable.ic_outlined_face)
                                            .transformations(CircleCropTransformation())
                                            .build(),
                                        contentDescription = "Image",
                                        modifier = Modifier.size(56.dp),
                                        filterQuality = FilterQuality.Low
                                    )
                                }
                            },
                            supportingContent = { Text(text = crew.type.toString()) }
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .navigationBarsPadding()
        )
    }
}