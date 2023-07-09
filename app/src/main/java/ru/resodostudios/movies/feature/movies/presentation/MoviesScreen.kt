package ru.resodostudios.movies.feature.movies.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.resodostudios.movies.core.presentation.components.RetrySection
import ru.resodostudios.movies.core.presentation.navigation.Screens
import ru.resodostudios.movies.feature.movie.presentation.components.MovieCard
import ru.resodostudios.movies.feature.movies.presentation.components.SearchBar

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel(),
    scope: CoroutineScope,
    drawerState: DrawerState
) {

    val movies by viewModel.movies.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isError by viewModel.isError.collectAsStateWithLifecycle()

    Surface(Modifier.fillMaxSize()) {
        SearchBar(
            viewModel = viewModel,
            movies = movies,
            navController = navController,
            onMenuClick = { scope.launch { drawerState.open() } }
        )

        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn()
        ) {
            LazyColumn(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 76.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onNavigate = { navController.navigate(Screens.Movie.route + "/${movie.id}") },
                        onDelete = { }
                    )
                }
            }
        }

        AnimatedVisibility(visible = isLoading, exit = fadeOut(), enter = fadeIn()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(visible = isError, exit = fadeOut(), enter = fadeIn()) {
            RetrySection(onClick = { viewModel.getMovies() })
        }
    }
}