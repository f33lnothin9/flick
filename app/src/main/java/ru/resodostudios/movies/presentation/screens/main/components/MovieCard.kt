package ru.resodostudios.movies.presentation.screens.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.resodostudios.movies.data.models.Movie
import ru.resodostudios.movies.presentation.components.CoilImage
import ru.resodostudios.movies.presentation.navigation.Screens
import ru.resodostudios.movies.presentation.ui.theme.Typography

@ExperimentalMaterial3Api
@Composable
fun MovieCard(movie: Movie, navController: NavController) {

    Card(
        onClick = { navController.navigate(Screens.Movie.route + "/${movie.id}") }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CoilImage(
                url = movie.image.medium,
                height = 128.dp,
                width = 91.dp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.height(128.dp),
                content = {
                    Text(
                        text = movie.name,
                        style = Typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = movie.rating.average.toString(),
                            style = Typography.bodyMedium
                        )
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "Rating"
                        )
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
            )
        }
    }
}