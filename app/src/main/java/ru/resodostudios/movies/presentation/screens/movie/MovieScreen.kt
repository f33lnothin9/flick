package ru.resodostudios.movies.presentation.screens.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.resodostudios.movies.presentation.components.CoilImage
import ru.resodostudios.movies.presentation.screens.main.MainViewModel
import ru.resodostudios.movies.presentation.screens.movie.components.MovieTopBar
import ru.resodostudios.movies.presentation.ui.theme.Typography

@ExperimentalMaterial3Api
@Composable
fun MovieScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel(), movieId: String) {

    val currentMovie = viewModel.movies
        .observeAsState(listOf()).value
        .firstOrNull { it.id == movieId.toInt() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MovieTopBar(
                title = currentMovie?.name ?: "",
                scrollBehavior = scrollBehavior,
                onNavClick = {
                    navController.navigateUp()
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .padding(it),
                content = {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                            content = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    content = {
                                        currentMovie?.let { movie ->
                                            CoilImage(
                                                url = movie.image.medium,
                                                width = 142.dp,
                                                height = 200.dp
                                            )
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically,
                                            content = {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(
                                                        text = currentMovie?.rating?.average.toString(),
                                                        style = Typography.labelLarge
                                                    )
                                                    Icon(
                                                        modifier = Modifier.size(20.dp),
                                                        imageVector = Icons.Rounded.Star,
                                                        contentDescription = "Rating"
                                                    )
                                                }
                                                Text(
                                                    text = currentMovie?.premiered?.take(4) ?: "",
                                                    style = Typography.labelLarge
                                                )
                                                Row(
                                                    content = {
                                                        currentMovie?.genres?.take(2)?.forEach { genres ->
                                                            Text(
                                                                text = "$genres ",
                                                                style = Typography.labelLarge
                                                            )
                                                        }
                                                    }
                                                )
                                                Text(
                                                    text = currentMovie?.language ?: "",
                                                    style = Typography.labelLarge
                                                )
                                            }
                                        )
                                        Divider()
                                        Spacer(modifier = Modifier.height(2.dp))
                                    }
                                )
                            }
                        )
                    }
                    item {
                        Text(
                            text = HtmlCompat.fromHtml(currentMovie?.summary ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
                            style = Typography.bodyLarge
                        )
                    }
                }
            )
        }
    )

}