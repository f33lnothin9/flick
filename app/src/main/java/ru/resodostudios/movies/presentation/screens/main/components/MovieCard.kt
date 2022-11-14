package ru.resodostudios.movies.presentation.screens.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.resodostudios.movies.data.models.Movie
import ru.resodostudios.movies.presentation.navigation.Screens
import ru.resodostudios.movies.presentation.ui.theme.Typography
import ru.resodostudios.movies.util.CoilImage

@Composable
fun MovieCard(movie: Movie, navController: NavController) {

    Card(modifier = Modifier.clickable { navController.navigate(Screens.Movie.route) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CoilImage(url = movie.image.medium)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = movie.name,
                    style = Typography.titleLarge
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = movie.rating.average.toString(),
                        style = Typography.bodyMedium
                    )
                    Icon(modifier = Modifier.size(20.dp), imageVector = Icons.Rounded.Star, contentDescription = "Rating")
                }
                Row {
                    movie.genres.take(2).forEach {
                        Text(
                            text = "$it ",
                            style = Typography.bodyMedium
                        )
                    }
                }
                Text(
                    text = movie.language,
                    style = Typography.bodyMedium
                )
                Text(
                    text = movie.premiered,
                    style = Typography.bodyMedium
                )
            }
        }
    }

}