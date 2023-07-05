package ru.resodostudios.movies.feature.movie.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.resodostudios.movies.core.presentation.components.CoilImage
import ru.resodostudios.movies.core.presentation.components.RetrySection
import ru.resodostudios.movies.core.presentation.theme.Typography
import ru.resodostudios.movies.feature.movie.data.model.Movie
import ru.resodostudios.movies.feature.movie.presentation.components.MovieTopBar

@ExperimentalMaterial3Api
@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel(),
    movieId: String
) {

    viewModel.getMovie(movieId)

    val movie = viewModel.movie.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val isError = viewModel.isError.collectAsStateWithLifecycle().value
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MovieTopBar(
                scrollBehavior = scrollBehavior,
                onNavIconClick = { navController.navigateUp() },
                actions = {
                    IconButton(onClick = { }) {
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
            AnimatedVisibility(visible = !isLoading, enter = fadeIn()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .padding(it),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        item {
                            Header(movie = movie)
                        }
                        item {
                            Text(
                                text = HtmlCompat.fromHtml(
                                    movie.summary ?: "",
                                    HtmlCompat.FROM_HTML_MODE_COMPACT
                                ).toString(),
                                style = Typography.bodyLarge
                            )
                        }
                    }
                )
            }
        }
    )

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (isError) {
        RetrySection(onClick = { viewModel.getMovie(movieId) })
    }
}

@Composable
private fun Header(movie: Movie?) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        movie?.let { movie ->
            movie.image?.let {
                it.medium?.let { it1 ->
                    CoilImage(
                        url = it1,
                        width = 125.dp,
                        height = 176.dp
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = movie?.name ?: "",
                style = Typography.headlineSmall,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = movie?.rating?.average.toString(),
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
                    text = movie?.premiered?.take(4) + " • ${movie?.genres?.get(0)}, ${
                        movie?.genres?.get(1)
                    }",
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = (movie?.network?.country?.name
                        ?: "Unknown") + ", ${movie?.averageRuntime} minutes",
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = movie?.language ?: "Unknown",
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    Spacer(modifier = Modifier.requiredHeight(12.dp))

    Divider()
}