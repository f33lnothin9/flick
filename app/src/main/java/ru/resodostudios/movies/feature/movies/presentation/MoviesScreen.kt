package ru.resodostudios.movies.feature.movies.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.resodostudios.movies.feature.movies.presentation.components.MovieCard
import ru.resodostudios.movies.feature.movies.presentation.components.SearchBar

@ExperimentalMaterial3Api
@Composable
fun MoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel(),
    scope: CoroutineScope,
    drawerState: DrawerState
) {

    val movies by viewModel.movies.collectAsStateWithLifecycle()

    Surface(Modifier.fillMaxSize()) {
        SearchBar(
            viewModel = viewModel,
            movies = movies,
            navController = navController,
            onMenuClick = { scope.launch { drawerState.open() } }
        )

        LazyColumn(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .statusBarsPadding()
                        .requiredHeight(70.dp)
                )
            }
            items(movies) { movie ->
                MovieCard(movie = movie, navController = navController)
            }
        }
    }
}