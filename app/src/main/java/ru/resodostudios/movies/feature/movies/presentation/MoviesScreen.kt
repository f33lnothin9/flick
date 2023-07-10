package ru.resodostudios.movies.feature.movies.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.resodostudios.movies.core.presentation.components.RetrySection
import ru.resodostudios.movies.core.presentation.navigation.Screens
import ru.resodostudios.movies.feature.movie.presentation.components.MovieCard
import ru.resodostudios.movies.feature.movies.domain.util.MoviesEvent
import ru.resodostudios.movies.feature.movies.domain.util.MoviesState
import ru.resodostudios.movies.feature.movies.presentation.components.SearchBar

@ExperimentalMaterial3Api
@Composable
fun MoviesScreen(
    navController: NavController,
    state: MoviesState,
    onEvent: (MoviesEvent) -> Unit,
    onRetry: () -> Unit,
    drawerState: DrawerState
) {

    val scope = rememberCoroutineScope()

    Surface(Modifier.fillMaxSize()) {
        SearchBar(
            onSearch = { onEvent(MoviesEvent.Search(it)) },
            movies = state.movies,
            navController = navController,
            onMenuClick = { scope.launch { drawerState.open() } },
            onClearSearch = { onEvent(MoviesEvent.Search("")) }
        )

        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn()
        ) {
            LazyColumn(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 76.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onNavigate = { navController.navigate(Screens.Movie.route + "/${movie.id}") },
                        onDelete = { }
                    )
                }
            }
        }

        AnimatedVisibility(visible = state.isLoading, exit = fadeOut(), enter = fadeIn()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(visible = state.isError, exit = fadeOut(), enter = fadeIn()) {
            RetrySection(onClick = onRetry)
        }
    }
}