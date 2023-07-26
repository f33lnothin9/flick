package ru.resodostudios.flick.feature.movie.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
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
    var maxLines by remember { mutableStateOf(3) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MovieTopBar(
                scrollBehavior = scrollBehavior,
                onNavIconClick = { navController.navigateUp() },
                actions = {
                    IconButton(
                        onClick = { onEvent(FavoriteEvent.AddMovie(state.movie)) }
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
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .padding(it),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        item {
                            Header(movie = state.movie)
                        }
                        item {
                            Body(
                                movie = state.movie,
                                onSummaryClick = { maxLines = Int.MAX_VALUE },
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

@Composable
private fun Header(movie: Movie) {
    Row(
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = movie.rating?.average.toString(),
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Outlined.StarRate,
                    contentDescription = "Star"
                )
            }

            Column {
                Text(
                    text = movie.premiered?.take(4) + " • ${movie.genres?.get(0)}, ${
                        movie.genres?.get(1)
                    }",
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

    Divider()
}

@ExperimentalMaterial3Api
@Composable
private fun Body(movie: Movie, onSummaryClick: () -> Unit, maxLines: Int) {

    Spacer(modifier = Modifier.height(8.dp))
    Card(
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
                    movie.summary ?: "",
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                ).toString(),
                style = Typography.bodyLarge,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}