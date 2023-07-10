package ru.resodostudios.movies.feature.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.resodostudios.movies.core.presentation.navigation.Screens
import ru.resodostudios.movies.feature.favorites.domain.util.FavoritesState
import ru.resodostudios.movies.feature.favorites.domain.util.FavoriteEvent
import ru.resodostudios.movies.feature.favorites.presentation.components.FavoriteCard

@ExperimentalMaterial3Api
@Composable
fun FavoritesScreen(
    state: FavoritesState,
    navController: NavController,
    drawerState: DrawerState,
    onEvent: (FavoriteEvent) -> Unit
) {

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Favorites") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            columns = GridCells.Adaptive(110.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = innerPadding
        ) {
            items(state.movies) { movie ->
                FavoriteCard(
                    imageUrl = movie.image.toString(),
                    onNavigate = { navController.navigate(Screens.Movie.route + "/${movie.id}") },
                    onDelete = { onEvent(FavoriteEvent.DeleteMovie(movie)) }
                )
            }
        }
    }
}