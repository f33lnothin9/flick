package ru.resodostudios.movies.presentation.screens.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.resodostudios.movies.presentation.screens.main.MainViewModel
import ru.resodostudios.movies.presentation.screens.movie.components.MovieTopBar
import ru.resodostudios.movies.presentation.components.CoilImage

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
                modifier = Modifier.padding(it),
                content = {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                            content = {
                                currentMovie?.let { movie ->
                                    CoilImage(
                                        url = movie.image.original,
                                        width = 320.dp,
                                        height = 320.dp
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    )

}