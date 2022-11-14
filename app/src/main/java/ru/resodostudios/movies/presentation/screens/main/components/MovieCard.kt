package ru.resodostudios.movies.presentation.screens.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.resodostudios.movies.data.models.Movie

@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = movie.name)
        }
    }
}