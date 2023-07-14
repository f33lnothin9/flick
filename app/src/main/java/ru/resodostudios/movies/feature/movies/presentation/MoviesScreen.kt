package ru.resodostudios.movies.feature.movies.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import ru.resodostudios.movies.feature.movies.domain.util.MoviesEvent
import ru.resodostudios.movies.feature.movies.presentation.components.MovieCard
import ru.resodostudios.movies.feature.search.presentation.components.SearchBar

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MoviesScreen(
    navController: NavController,
    state: MoviesUiState,
    onEvent: (MoviesEvent) -> Unit,
    onRetry: () -> Unit,
    drawerState: DrawerState
) {

    val scope = rememberCoroutineScope()

    Surface(Modifier.fillMaxSize()) {
        SearchBar(
            onSearch = { onEvent(MoviesEvent.Search(it)) },
            movies = state.searchedMovies,
            navController = navController,
            onMenuClick = { scope.launch { drawerState.open() } },
            onClearSearch = { onEvent(MoviesEvent.Search(it)) }
        )

        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn()
        ) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 76.dp),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                columns = StaggeredGridCells.Adaptive(150.dp)
            ) {
                items(state.movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onNavigate = { navController.navigate(Screens.Movie.route + "/${movie.id}") }
                    )
                }
            }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (state.isError) RetrySection(onClick = onRetry)
    }
}