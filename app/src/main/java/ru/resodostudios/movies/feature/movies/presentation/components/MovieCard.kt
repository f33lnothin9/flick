package ru.resodostudios.movies.feature.movies.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.resodostudios.movies.core.presentation.components.CoilImage
import ru.resodostudios.movies.core.presentation.navigation.Screens
import ru.resodostudios.movies.core.presentation.theme.Typography
import ru.resodostudios.movies.feature.movies.data.model.Movie

@ExperimentalMaterial3Api
@Composable
fun MovieCard(movie: Movie, navController: NavController) {

    Card(
        onClick = { navController.navigate(Screens.Movie.route + "/${movie.id}") },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            movie.image?.let {
                it.medium?.let { it1 ->
                    CoilImage(
                        url = it1,
                        height = 98.dp,
                        width = 70.dp
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                content = {
                    movie.name?.let {
                        Text(
                            text = it,
                            style = Typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.requiredHeight(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = movie.rating?.average.toString(),
                            style = Typography.bodyMedium
                        )

                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Outlined.StarRate,
                            contentDescription = "Star"
                        )
                    }

                    Text(
                        text = movie.genres?.get(0) ?: "",
                        style = Typography.bodyMedium,
                        textAlign = TextAlign.Start
                    )
                }
            )
        }
    }
}