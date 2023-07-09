package ru.resodostudios.movies.feature.favorites.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.resodostudios.movies.core.presentation.navigation.Screens
import ru.resodostudios.movies.feature.favorites.domain.util.MovieState
import ru.resodostudios.movies.feature.favorites.presentation.components.FavoriteCard

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(state: MovieState, navController: NavController) {
    
    Scaffold { innerPadding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 76.dp),
            columns = StaggeredGridCells.Adaptive(150.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = innerPadding
        ) {
            items(state.movies) { movie ->
                FavoriteCard(
                    imageUrl = movie.image.toString(),
                    onNavigate = { navController.navigate(Screens.Movie.route + "/${movie.id}") },
                    onDelete = { }
                )
            }
        }
    }
}