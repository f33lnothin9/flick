package ru.resodostudios.movies.feature.favorites.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.resodostudios.movies.feature.favorites.domain.util.MovieState

@Composable
fun FavoritesScreen(state: MovieState) {
    
    Scaffold { innerPadding ->
        LazyColumn(contentPadding = innerPadding ) {
            items(state.movies) { movie ->
                Text(text = movie.id.toString())
            }
        }
    }
}